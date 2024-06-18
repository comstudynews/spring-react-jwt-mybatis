package com.mysite.reactbbs.member.dao;

import com.mysite.reactbbs.member.domain.Member;
import com.mysite.reactbbs.member.dto.param.CreateMemberParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MemberDao {

	Member findById(String id);

	Integer isExistUserId(String id);

	Integer createMember(CreateMemberParam param);
}
