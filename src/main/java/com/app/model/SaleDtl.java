package com.app.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.Data;
@Entity
@Table(name="sale_dtl_tab")
@Data
public class SaleDtl {
	@Id
	@GeneratedValue
	@Column(name = "sale__dtl_id_col")
	private Integer id;

	@Transient
	private Integer slno;

	@Column(name = "sale__dtl_qty_col")
	private Integer qty;
	
	@ManyToOne
	@JoinColumn(name = "part_id_fk")
	private Part part;//HAS-A
	
	/**
	 * Every PurchaseDtl must be linked 
	 * with its PurchaseOrder parent(Screen#1)
	 */
	@ManyToOne
	@JoinColumn(name="so_id_fk")
	private SaleOrder so; //HAS-A

	}
