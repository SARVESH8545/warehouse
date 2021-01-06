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
@Data
@Entity
@Table(name="good_recevice_note_tab")
public class Grn {
	@Id
	@GeneratedValue(generator ="grn_seq_name")
	@SequenceGenerator(name="grn_seq_name",sequenceName="grn_seq_name" )
	@Column(name="grn_id_col")
	private Integer id;
	
	@Column(name="grn_code_col")
	private String grnCode;
	
	@Column(name="grn_type_col")
	private String grnType;
	
	@Column(name="description_col")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="po_id_fk",unique = true)
	private PurchaseOrder po;//1..1
	
	@OneToMany(mappedBy = "grn")
	private List<GrnDtl> dtls;

}
