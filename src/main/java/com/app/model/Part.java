package com.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="part_tab")
public class Part {
	@Id
	@GeneratedValue(generator = "part_seq_name")
	@SequenceGenerator(name = "part_seq_name", sequenceName = "part_seq")
	@Column(name="part_id_col")
	private Integer id;

	@Column(name="part_code_col")
	private String partCode;

	@Column(name="part_wdth_col")
	private Double partWdth;

	@Column(name="part_len_col")
	private Double partLen;

	@Column(name="part_hgh_col")
	private Double partHgh;

	@Column(name="part_cost_col")
	private Double baseCost;

	@Column(name="part_curr_col")
	private String baseCurr;

	@Column(name="part_desc_col")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="uom_id_col_fk")
	private Uom uom;//HAS-A
	
}
