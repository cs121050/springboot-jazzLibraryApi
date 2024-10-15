package com.nicosarr.jazzLibraryAPI.TableRowCount;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.nicosarr.jazzLibraryAPI.Artist.Artist;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.sql.ResultSet;

@Entity
@Table(name = "TableRowCount")  
@JacksonXmlRootElement(localName = "tableRowCount")
public class TableRowCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int table_row_count_id;  // Assuming there's a primary key

    @Column(name = "table_id")
    private int table_id;
    
    @Column(name = "table_name")
    private int table_name;    

    @Column(name = "count")  // Assuming there's a count column
    private int count;

	public TableRowCount(int table_row_count_id, int table_id, int table_name, int count) {
		this.table_row_count_id = table_row_count_id;
		this.table_id = table_id;
		this.table_name = table_name;
		this.count = count;
	}
	
	public TableRowCount() {

	}

	public int getTable_row_count_id() {
		return table_row_count_id;
	}

	public void setTable_row_count_id(int table_row_count_id) {
		this.table_row_count_id = table_row_count_id;
	}

	public int getTable_id() {
		return table_id;
	}

	public void setTable_id(int table_id) {
		this.table_id = table_id;
	}

	public int getTable_name() {
		return table_name;
	}

	public void setTable_name(int table_name) {
		this.table_name = table_name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "TableRowCount [table_row_count_id=" + table_row_count_id + ", table_id=" + table_id + ", table_name="
				+ table_name + ", count=" + count + ", getTable_row_count_id()=" + getTable_row_count_id()
				+ ", getTable_id()=" + getTable_id() + ", getTable_name()=" + getTable_name() + ", getCount()="
				+ getCount() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
	
	
	
	
    
    
    

}