package br.com.fileUpload.domain.model;
import io.gumga.domain.GumgaModel; //TODO RETIRAR OS IMPORTS DESNECESSÁRIOS
import io.gumga.domain.GumgaModelUUID;
import io.gumga.domain.GumgaMultitenancy;
import java.io.Serializable;
import java.util.*;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import io.gumga.domain.domains.*;
import org.hibernate.envers.Audited;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Columns;

@GumgaMultitenancy
@Audited
@Entity(name = "Document")
@Table(name = "Document", indexes = {
    @Index(name = "Document_gum_oi", columnList = "oi")
})
@SequenceGenerator(name = GumgaModel.SEQ_NAME, sequenceName = "SEQ_DocumentID")
public class Document extends GumgaModel<Long> {


    @Columns(columns = {
    @Column(name = "file_name"),
    @Column(name = "file_size"),
    @Column(name = "file_type"),
    @Column(name = "file_bytes",length = 50*1024*1024)
    })
	private GumgaFile file;

    public Document() {}

	public GumgaFile getFile() {
		return this.file;
	}
	public void setFile(GumgaFile file) {
		this.file = file;
	}
}
