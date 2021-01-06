package com.app.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
@Table(name="sale_order_tab")
@Entity
@Data
public class SaleOrder {
	@Id
	@GeneratedValue(generator = "sale_order_seq_name")
	@SequenceGenerator(name = "sale_order_seq_name",sequenceName = "sale_order_seq")
	@Column(name="sale_id_col")
	private Integer id;
	
	@Column(name="ordr_code_col")
	private String orderCode;
	
	@Column(name = "ref_number_col")
	private String referenceNumber;
	
	@Column(name = "stk_mode_col")
	private String stockMode;
	
	@Column(name = "stck_source_col")
	private String stockSource;
	
	@Column(name ="dflt_stus_col")
	private String status;
	
	@Column(name ="desc_col")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "porder_shipCode_fk")
	private ShipmentType shipmentCode;

	@ManyToOne
	@JoinColumn(name = "porder_wu_vendor_fk")
	private WhUserType customer;
	
	@OneToMany(mappedBy = "so")
	private List<SaleDtl>Dtls;

}
