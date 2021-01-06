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
@Table(name="shipment_type_tab")
public class ShipmentType {
	@Id
	@GeneratedValue(generator = "shipment_type_seq_name")
	@SequenceGenerator(name = "shipment_type_seq_name", sequenceName = "shipment_type_seq")
	//@GeneratedValue(generator = "shipment_type_seq_name")
	//@GenericGenerator(name = "shipment_type_seq_name",
	              //strategy = "com.app.generator.ShipmentTypeGen")
	@Column(name = "ship_id_col",length = 5)
	private Integer id;

	@Column(name = "shipment_mode_col" ,length = 10)
	private String shipmentMode;

	@Column(name = "shipment_code_col",length = 15, nullable = false)
	private String shipmentCode;

	@Column(name = "enable_shipment_col",length = 4, nullable = false)
	private String enableShipment;

	@Column(name = "shipment_grade_col",length = 3, nullable = false)
	private String shipmentGrade;

	@Column(name = "description_col",length = 180, nullable = false)
	private String description;
}
