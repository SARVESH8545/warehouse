package com.app.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

//import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@Table(name="uom_tab")
public class Uom {

	@Id
	@GeneratedValue(generator = "uom_seq_name")
	@SequenceGenerator(name = "uom_seq_name", sequenceName = "uom_seq")

	//@GeneratedValue(generator = "uom_seq_name")
	//@GenericGenerator(name = "uom_seq_name",
	//              strategy = "com.app.generator.UomGen")
	@Column(name= "uom_id_col")
	private Integer id;

	@Column(name= "uom_type_col" ,length = 25, nullable = false)	
	private String uomType;

	@Column(name= "uom_model_col", length = 10, nullable = false)
	private String uomModel;

	@Column(name= "description_col",length = 180, nullable = false)
	private String description;
}
