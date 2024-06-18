package com.mysite.reactbbs.bbs.dao;

import java.util.List;
import com.mysite.reactbbs.bbs.domain.Bbs;
import com.mysite.reactbbs.bbs.dto.param.BbsCountParam;
import com.mysite.reactbbs.bbs.dto.param.BbsListParam;
import com.mysite.reactbbs.bbs.dto.param.CreateBbsAnswerParam;
import com.mysite.reactbbs.bbs.dto.param.CreateBbsParam;
import com.mysite.reactbbs.bbs.dto.param.CreateReadCountParam;
import com.mysite.reactbbs.bbs.dto.param.UpdateBbsParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;



@Mapper
@Repository
public interface BbsDao {

	List<Bbs> getBbsSearchPageList(BbsListParam param);
	//Integer getBbsCount(BbsCountParam param);
	
	Integer getBbsCount(BbsListParam param);

	Bbs getBbs(Integer seq);
	Integer createBbsReadCountHistory(CreateReadCountParam param);
	Integer increaseBbsReadCount(Integer seq);

	void createBbs(CreateBbsParam param);

	Integer updateBbsStep(Integer parentSeq);
	Integer getBbsAnswerCount(Integer parentSeq);
	void createBbsAnswer(CreateBbsAnswerParam param);

	Integer updateBbs(UpdateBbsParam param);

	Integer deleteBbs(Integer seq);
}
