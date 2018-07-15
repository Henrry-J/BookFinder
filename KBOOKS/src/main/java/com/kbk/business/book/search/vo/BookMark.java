package com.kbk.business.book.search.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BOOK_MARK")
public class BookMark implements Serializable {
    private static final long serialVersionUID = -5086723895786151516L;
    @Id
    @Column(name="LOGINID", nullable=false)
    private String loginId;
    @Column(name="ENTDT")
    private Date entDt;
    @Id
    @Column(name="TITLE", nullable=false)
    private String title;
    @Column(name="THUMBNAIL")
    private String thumbnail;
    @Column(name="URL")
    private String url;

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public Date getEntDt() {
        return this.entDt;
    }

    public void setEntDt(Date entDt) {
        this.entDt = entDt;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.entDt == null ? 0 : this.entDt.hashCode());
        result = 31 * result + (this.loginId == null ? 0 : this.loginId.hashCode());
        result = 31 * result + (this.thumbnail == null ? 0 : this.thumbnail.hashCode());
        result = 31 * result + (this.title == null ? 0 : this.title.hashCode());
        result = 31 * result + (this.url == null ? 0 : this.url.hashCode());
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
        BookMark other = (BookMark)obj;
        if (this.entDt == null ? other.entDt != null : !this.entDt.equals(other.entDt)) {
            return false;
        }
        if (this.loginId == null ? other.loginId != null : !this.loginId.equals(other.loginId)) {
            return false;
        }
        if (this.thumbnail == null ? other.thumbnail != null : !this.thumbnail.equals(other.thumbnail)) {
            return false;
        }
        if (this.title == null ? other.title != null : !this.title.equals(other.title)) {
            return false;
        }
        if (this.url == null ? other.url != null : !this.url.equals(other.url)) {
            return false;
        }
        return true;
    }
}
