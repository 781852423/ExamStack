package com.examstack.management.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examstack.common.domain.exam.ExamPaper;
import com.examstack.common.domain.exam.Paper;
import com.examstack.common.domain.exam.Paper2Part;
import com.examstack.common.domain.exam.PaperPart;
import com.examstack.common.domain.question.Question;
import com.examstack.common.domain.question.QuestionContent;
import com.examstack.common.domain.question.QuestionQueryResult;
import com.examstack.common.domain.question.QuestionStruts;
import com.examstack.common.util.CustomXWPFDocument;
import com.examstack.common.util.Page;
import com.examstack.management.persistence.ExamPaperMapper;
import com.examstack.management.persistence.QuestionMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service("examPaperService")
public class ExamPaperServiceImpl implements ExamPaperService {

	@Autowired
	private ExamPaperMapper examPaperMapper;
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private QuestionService questionService;

	@Override
	public List<ExamPaper> getExamPaperList(String searchStr, String paperType, Page<ExamPaper> page) {
		// TODO Auto-generated method stub
		return examPaperMapper.getExamPaperList(searchStr, paperType, page);
	}

	@Override
	public void insertExamPaper(ExamPaper examPaper) {
		// 根据examPaper再插入其他表中
		examPaperMapper.insertExamPaper(examPaper);
		
		List<PaperPart> parts = examPaper.getPaperParts();
		int paperId = examPaper.getId();
		for(PaperPart pp : parts)
		{
			pp.setPaperId(paperId);
		}
		
		// 插入这些试题组成部分的内容，但还没做与paper的关联
		if(parts != null && parts.size() > 0)
		{
			for(PaperPart p : parts)
			{
				examPaperMapper.insertPaperPart(p);
			}
			
		}
			
		//List<Paper2Part> p2plist = new ArrayList<Paper2Part>();
		// 插入关联关系
		for(PaperPart p2 : parts)
		{
			Paper2Part p2p = new Paper2Part();
			p2p.setPaperId(p2.getPaperId());
			p2p.setPaperPartId(p2.getId());
			
			examPaperMapper.insertPaper2PartRelation(p2p);
			//p2plist.add(p2p);
		}
		
	}

	@Override
	@Transactional
	public void createExamPaper(HashMap<Integer, HashMap<Integer, List<QuestionStruts>>> questionMap,
			HashMap<Integer, Integer> questionTypeNum, HashMap<Integer, Float> questionTypePoint,
			HashMap<Integer, Float> knowledgePointRate, ExamPaper examPaper) {
		// TODO Auto-generated method stub

		HashMap<Integer,String> knowledgeMap = (HashMap<Integer, String>) questionService.getKnowledgePointMap(0);
		HashMap<Integer,String> typeMap = (HashMap<Integer, String>) questionService.getQuestionTypeMap();
		Paper paper = new Paper(questionMap, questionTypeNum, questionTypePoint, knowledgePointRate, knowledgeMap, typeMap);
		try {
			paper.createPaper();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e1.getMessage());
		}

		try {
			HashMap<Integer, QuestionStruts> paperQuestionMap = paper.getPaperQuestionMap();

			Iterator<Integer> it = paperQuestionMap.keySet().iterator();
			List<Integer> idList = new ArrayList<Integer>();
			while (it.hasNext()) {
				idList.add(it.next());
			}
			List<QuestionQueryResult> questionList = questionMapper.getQuestionAnalysisListByIdList(idList);
			for (QuestionQueryResult q : questionList) {
				q.setQuestionPoint(questionTypePoint.get(q.getQuestionTypeId()));
			}
			Gson gson = new Gson();
			examPaper.setContent(gson.toJson(questionList));
			examPaperMapper.insertExamPaper(examPaper);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}

	}

	/*
	 * 已经获取part内容，以及part下面所属的questions内容
	 * @see com.examstack.management.service.ExamPaperService#getExamPaperById(int)
	 */
	@Override
	public ExamPaper getExamPaperById(int examPaperId) {
		// TODO Auto-generated method stub
		ExamPaper ep = new ExamPaper();
		ep = examPaperMapper.getExamPaperById(examPaperId);
		// 获取ep的parts getPaperPartsByPaperId
		List<PaperPart> ppList= getPaperPartsByPaperId(examPaperId);
		ep.setPaperParts(ppList);
		return ep;
	}

	@Override
	public void updateExamPaper(ExamPaper examPaper) {
		// TODO Auto-generated method stub
		examPaperMapper.updateExamPaper(examPaper);
	}

	@Override
	public void deleteExamPaper(int id) {
		// TODO Auto-generated method stub
		examPaperMapper.deleteExamPaper(id);
	}

	@Override
	public List<ExamPaper> getEnabledExamPaperList(String userName, Page<ExamPaper> page) {
		// TODO Auto-generated method stub
		return examPaperMapper.getEnabledExamPaperList(userName, page);
	}

	@Override
	public void generateDoc(ExamPaper examPaper, String path) throws Exception {
		// TODO Auto-generated method stub
		String basePath = System.getProperty("catalina.base") + ",webapps,";
		String filePath = basePath + "Management,resources,template,doc_tmp.docx";

		//String filePath = basePath + "files,resources,template,doc_tmp.docx";
		filePath = filePath.replace(',', File.separatorChar);
		OPCPackage pack = POIXMLDocument.openPackage(filePath);
		CustomXWPFDocument doc = new CustomXWPFDocument(pack);
		Gson gson = new Gson();
		String content = examPaper.getContent();
		List<QuestionQueryResult> questionList = gson.fromJson(content, new TypeToken<List<QuestionQueryResult>>(){}.getType());
		// 设置标题
		XWPFParagraph p1 = doc.createParagraph();
		// 设置字体对齐方式
		p1.setAlignment(ParagraphAlignment.CENTER);
		p1.setVerticalAlignment(TextAlignment.TOP);
		// 第一页要使用p1所定义的属性
		XWPFRun r1 = p1.createRun();
		// 设置字体是否加粗
		r1.setBold(true);
		r1.setFontSize(20);
		// 设置使用何种字体
		r1.setFontFamily("Courier");
		// 设置上下两行之间的间距
		r1.setTextPosition(40);
		r1.setText(examPaper.getName());
		
		if(questionList == null ) return;
		for(QuestionQueryResult question : questionList){
			QuestionContent questionContent = gson.fromJson(question.getContent(), QuestionContent.class);
			QuestionContent questionParentContent = gson.fromJson(question.getParentContent(), QuestionContent.class);
			// 设置试题标题
			XWPFParagraph t = doc.createParagraph(); // 代表一个段落
			// 设置字体对齐方式
			t.setAlignment(ParagraphAlignment.LEFT);
			t.setVerticalAlignment(TextAlignment.TOP);
			
			XWPFRun rt = t.createRun(); // 代表具有相同属性的一段文本
			// 设置字体是否加粗
			rt.setBold(false);
			rt.setFontSize(15);
			// 设置使用何种字体
			rt.setFontFamily("Courier");
			// 设置上下两行之间的间距
			rt.setTextPosition(40);
			
			// 设置题目主干的内容
			if(questionParentContent != null)
			{
				rt.setText(questionParentContent.getTitle());
				if( (! "".equals(questionParentContent.getTitleImg())) && (questionParentContent.getTitleImg() != null) )
				{
					String parentTitlePicPath = basePath.replace(',',File.separatorChar);
					File parentTitlePic = new File(parentTitlePicPath + questionParentContent.getTitleImg());
					
					if(parentTitlePic.exists())
					{
						BufferedImage sourceImg = ImageIO.read(new FileInputStream(parentTitlePic));
						
						String ind = doc.addPictureData(new FileInputStream(parentTitlePic), XWPFDocument.PICTURE_TYPE_JPEG);
						rt.setText(parentTitlePic.getAbsolutePath());
						System.out.println("parentPic: " + parentTitlePic.getAbsolutePath());
						doc.createPicture(ind,doc.getAllPictures().size() -1, sourceImg.getWidth()/2, sourceImg.getHeight()/2);
						sourceImg.flush();
					}
				}
			}
			  
			// 设置试题标题
			XWPFParagraph t2 = doc.createParagraph();
			// 设置字体对齐方式
			t2.setAlignment(ParagraphAlignment.LEFT);
			t2.setVerticalAlignment(TextAlignment.TOP);
			
			XWPFRun rt2 = t2.createRun();
			// 设置字体是否加粗
			rt2.setBold(false);
			rt2.setFontSize(15);
			// 设置使用何种字体
			rt2.setFontFamily("Courier");
			// 设置上下两行之间的间距
			rt2.setTextPosition(40);
						
			if(questionContent != null)
			{
				rt2.setText(questionContent.getTitle() + "(" + question.getQuestionPoint() + "分)");
				
				if(!"".equals(questionContent.getTitleImg()) && questionContent.getTitleImg() != null){
					String titlePicPath = basePath.replace(',', File.separatorChar);
					File titlePic = new File(titlePicPath + questionContent.getTitleImg());
					
					if(titlePic.exists())
					{
						BufferedImage sourceImg = ImageIO.read(new FileInputStream(titlePic));
	
						String ind = doc.addPictureData(new FileInputStream(titlePic),
								XWPFDocument.PICTURE_TYPE_JPEG);
						doc.createPicture(ind, doc.getAllPictures().size() - 1,
								sourceImg.getWidth() / 2, sourceImg.getHeight() / 2);
						sourceImg.flush();
					}
				}
			}
			
			XWPFParagraph crt = doc.createParagraph();
			XWPFRun cr = crt.createRun();
			cr.setText("");
			//选择题和判断题增加选项
			if(question.getQuestionTypeId() == 1 || question.getQuestionTypeId() == 2 || question.getQuestionTypeId() == 3){
				for(Map.Entry<String, String> entry : questionContent.getChoiceList().entrySet()){
					// 设置试题标题
					XWPFParagraph c = doc.createParagraph();
					// 设置字体对齐方式
					c.setAlignment(ParagraphAlignment.LEFT);
					c.setVerticalAlignment(TextAlignment.TOP);
					
					XWPFRun rc = c.createRun();
					// 设置字体是否加粗
					rc.setBold(false);
					rc.setFontSize(15);
					// 设置使用何种字体
					rc.setFontFamily("Courier");
					// 设置上下两行之间的间距
					rc.setTextPosition(40);
					rc.setText(entry.getKey() + " " + entry.getValue());
					
					if(questionContent !=null && questionContent.getChoiceImgList() != null && questionContent.getChoiceImgList().containsKey(entry.getKey())){
						String picPath = basePath.replace(',', File.separatorChar) + questionContent.getChoiceImgList().get(entry.getKey());
						System.out.println(picPath);
						File picture = new File(picPath);
						if(picture.exists())
						{
						    BufferedImage sourceImg = ImageIO.read(new FileInputStream(picture));
						
						
							String ind = doc.addPictureData(new FileInputStream(picture),
									XWPFDocument.PICTURE_TYPE_JPEG);
							doc.createPicture(ind,doc.getAllPictures().size() - 1,
									sourceImg.getWidth() / 2, sourceImg.getHeight() / 2);
							sourceImg.flush();
						}
					}
					XWPFParagraph crta = doc.createParagraph();
					XWPFRun cra = crta.createRun();
					cra.setText("");
				}
			}
			
			
		}
		
		FileOutputStream out;
		try {
			File f = new File(path);
			if(!f.exists())
				f.mkdirs();
			
			out = new FileOutputStream(path + File.separatorChar + examPaper.getName() + ".docx");
			doc.write(out);
			out.close();
			System.out.println("success");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public PaperPart getPaperPartById(int partId) {
		// TODO Auto-generated method stub
		return examPaperMapper.getPaperPartById(partId);
	}

	/*
	 * 先删除之前的关系，然后添加新的关系
	 * @see com.examstack.management.service.ExamPaperService#updateExamPartQuestions(com.examstack.common.domain.exam.PaperPart)
	 */
	@Override
	public void updateExamPartQuestions(PaperPart pp) {
		examPaperMapper.deletePartQuestionsByPartId(pp.getId());
		examPaperMapper.updateExamPartQuestions(pp);
	
	}

	/*
	 * 根据paperId获取其不同的part，同时附上其questions内容
	 * @see com.examstack.management.service.ExamPaperService#getPaperPartsByPaperId(int)
	 */
	@Override
	public List<PaperPart> getPaperPartsByPaperId(int exampaperId) {
		// TODO Auto-generated method stub
		List<PaperPart> parts = examPaperMapper.getPaperPartsByPaperId(exampaperId);
		// 获取questions
		for(PaperPart pp : parts)
		{
			List<Integer> questionIds = examPaperMapper.getQuestionIdListByPaperPartId(pp.getId());
			// 根据ID，获取question
			List<Question> questions = null;
			if(questionIds != null && questionIds.size() > 0)
			{
				questions = questionMapper.getQuestionListByIdList(questionIds);
			}
			
			pp.setQuestions(questions);
		}
		return parts;
	}
}
