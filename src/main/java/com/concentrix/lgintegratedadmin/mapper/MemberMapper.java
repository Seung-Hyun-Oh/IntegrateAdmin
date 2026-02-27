package com.concentrix.lgintegratedadmin.mapper;

import com.concentrix.lgintegratedadmin.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    void save(Member member);
    Member findByEmail(@Param("email") String email);
}
