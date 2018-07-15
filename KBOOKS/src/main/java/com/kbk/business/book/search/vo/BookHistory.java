package com.kbk.business.book.search.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BOOK_HISTORY")
public class BookHistory implements Serializable {
    private static final long serialVersionUID = -4580777562175766420L;
    @Id
    @Column(name="LOGINID", nullable=false)
    private String loginId;
    @Id
    @Column(name="TITLE")
    private String title;
    @Column(name="ENTDT")
    private Date entDt;

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getEntDt() {
        return this.entDt;
    }

    public void setEntDt(Date entDt) {
        this.entDt = entDt;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.entDt == null ? 0 : this.entDt.hashCode());
        result = 31 * result + (this.loginId == null ? 0 : this.loginId.hashCode());
        result = 31 * result + (this.title == null ? 0 : this.title.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        BookHistory other = (BookHistory)obj;
        if (this.entDt == null ? other.entDt != null : !this.entDt.equals(other.entDt)) {
            return false;
        }
        if (this.loginId == null ? other.loginId != null : !this.loginId.equals(other.loginId)) {
            return false;
        }
        if (this.title == null ? other.title != null : !this.title.equals(other.title)) {
            return false;
        }
        return true;
    }
}
