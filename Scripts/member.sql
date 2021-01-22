DROP TABLE TBL_MEMBER;

CREATE TABLE TBL_MEMBER(
	member_id VARCHAR2(50),
	member_pw VARCHAR2(50),
	member_name VARCHAR2(50),
	member_age NUMBER(2),
	member_gender VARCHAR2(20),
	member_email VARCHAR2(50),
	member_zipcode VARCHAR2(10),
	member_address VARCHAR2(100),
	member_address_detail VARCHAR2(100),
	member_address_etc VARCHAR2(100),
	CONSTRAINT MEMBER_PK PRIMARY KEY(member_id)
);

SELECT * FROM TBL_MEMBER;