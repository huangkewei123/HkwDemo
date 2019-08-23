package com.example.demo.manipulation.entity.base;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@Table(name = "attachment")
public class Attachment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6550622276456824882L;
	@Column(name = "fileId",type = MySqlTypeConstant.INT,length = 11,isKey = true,isAutoIncrement = true)
	private Integer fileId;
	@Column(name = "fileName",type = MySqlTypeConstant.VARCHAR,length = 40)
	private String fileName;
	@Column(name = "filePath",type = MySqlTypeConstant.VARCHAR,length = 200)
	private String filePath;
	@Column(name = "fileType",type = MySqlTypeConstant.VARCHAR,length = 10)
	private String fileType;
	@Column(name = "classify",type = MySqlTypeConstant.VARCHAR,length = 10)
	private String classify;
	@Column(name = "appId",type = MySqlTypeConstant.VARCHAR,length = 10)
	private String appId;
	@Column(name = "fileSize",type = MySqlTypeConstant.INT,length = 10)
	private Long fileSize;
	@Column(name = "upTime",type = MySqlTypeConstant.DATETIME,length = 10)
	private Timestamp upTime;
	@Column(name = "lastModify",type = MySqlTypeConstant.DATETIME)
	private Timestamp lastModify;
	@Column(name = "lastModifier",type = MySqlTypeConstant.VARCHAR,length = 36)
	private String lastModifier;
	@Column(name = "ext",type = MySqlTypeConstant.VARCHAR,length = 200)
	private String ext;
	@Column(name = "seqno",type = MySqlTypeConstant.INT,length = 2)
	private String seqno;
}
