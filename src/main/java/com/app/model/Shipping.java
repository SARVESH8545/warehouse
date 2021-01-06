package com.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "shipping_tab")
public class Shipping {
	@Id
	@GeneratedValue(generator = "ship_seq_name")
	@SequenceGenerator(name = "ship_seq_name", sequenceName = "ship_seq")
	
	@Column(name = "shi_id_col")
	private Integer id;
	
	@Column(name = "ship_code_col")
	private String shippingCode;
	
	@Column(name = "ship_ref_num_col")
	private String shippingRefNum;
	
	@Column(name = "cour_ref_num_col")
	private String courierRefNum;
	
	@Column(name = "cont_dtl_col")
	private String contactDtls;
	
	@Column(name = "bill_addr_col")
	private String billToAddress;
	
	@Column(name = "ship_addr_col")
	private String shipToAddress;
	
	@Column(name = "desc_col")
	private String description;
}
