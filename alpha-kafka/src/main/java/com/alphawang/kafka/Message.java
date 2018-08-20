package com.alphawang.kafka;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@Accessors(chain = true)
public class Message {
	private Long id;
	private String name;
	private Date date;
}
