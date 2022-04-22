package com.Jclasses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NameNCount {

	String name;
	String type;
	int price;
	int count;
}
