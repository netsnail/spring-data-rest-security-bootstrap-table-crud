/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.rest.security;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Domain object for an item managed by the company.
 *
 * @author Greg Turnquist
 * @author Oliver Gierke
 */
@Entity(name="peop_product")
@Data
@RequiredArgsConstructor
public class Item {

	private @Id @GeneratedValue Long id;
	private final String province, category, company;
	@Column(name="product_name")
	private final String productName;
	@Column(name="op_type")
	private final String opType;
	@Column(name="zongju_no")
	private final String zongjuNo;
	@Column(name="approve_date")
	@Temporal(TemporalType.DATE)
	private final Date approveDate;

	Item() {
		province=null;
		opType=null; 
		category=null; 
		productName=null; 
		company=null; 
		zongjuNo=null;
		approveDate=null;
	}
}
