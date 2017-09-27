package com.examstack.common.util;

import com.examstack.common.util.file.PropertyReaderUtil;

public class QuestionFilterUtil {

	public static boolean checkIfFieldIsPersonalityTest(int fieldId)
	{
		boolean flag = false;
		// 获取目前配置的性格测试ID清单
		try
		{
			String strIds = PropertyReaderUtil.getCustomPropertyByPropertyStr("charactorTestFieldIds");
			String[] array = strIds.split(",");
			for(String s : array)
			{
				if(s.equals(Integer.toString(fieldId)))
				{
					flag = true;
					break;
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return flag;
	}
}
