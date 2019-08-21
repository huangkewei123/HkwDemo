package com.example.demo.manipulation.entity.base;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Attachment implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6550622276456824882L;
	private Integer fileId; 
	private String fileName;
	private String filePath;
	private String fileType;
	private String classify;
	private String appId;
	private Long fileSize;
	private Timestamp upTime;
	private Timestamp lastModify;
	private String lastModifier;
	private String ext;
	private String seqno;

	
	
}
