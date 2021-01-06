package com.app.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
@Data
@Entity
@Table(name="order_method_tab")
public class OrderMethod {
	@Id
	@GeneratedValue(generator = "ordr_method_seq_name")
	@SequenceGenerator(name = "ordr_method_seq_name", sequenceName = "ordr_method_seq")
	//@GeneratedValue(generator = "order_method_seq_name")
	//@GenericGenerator(name = "order_method_seq_name",
	//strategy = "com.app.generator.OrderMethodGen")
	@Column(name="ord_id_col")
	private Integer id;

	@Column(name="ord_mode_col")
	private String orderMode;

	@Column(name="ord_code_col")
	private String orderCode;

	@Column(name="ord_type_col")
	private String orderType;

	@ElementCollection
	@CollectionTable(name="ord_acpt_tab",
	joinColumns = @JoinColumn(name="ord_id_col"))
	@Column(name="ord_acpt_col")
	private List<String>orderAcpt;

	@Column(name="ord_desc_col")
	private String description;

}
