package com.kbk.business.book.search.vo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BOOK_USER")
public class BookUser implements Serializable {
    private static final long serialVersionUID = -783446623035016018L;
    @Id
    @Column(name="LOGINID", nullable=false)
    private String loginId;
    @Column(name="LOGINPW", nullable=false)
    private String loginPw;
    @Column(name="ENTDT", nullable=false)
    private Date entDt;
    @Column(name="LOGINDT")
    private Date loginDt;
    @Column(name="LOGOUTDT")
    private Date logoutDt;

    public String getLoginId() {
        return this.loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginPw() {
        return this.loginPw;
    }

    public void setLoginPw(String loginPw) {
        this.loginPw = loginPw;
    }

    public Date getEntDt() {
        return this.entDt;
    }

    public void setEntDt(Date entDt) {
        this.entDt = entDt;
    }

    public Date getLoginDt() {
        return this.loginDt;
    }

    public void setLoginDt(Date loginDt) {
        this.loginDt = loginDt;
    }

    public Date getLogoutDt() {
        return this.logoutDt;
    }

    public void setLogoutDt(Date logoutDt) {
        this.logoutDt = logoutDt;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.entDt == null ? 0 : this.entDt.hashCode());
        result = 31 * result + (this.loginDt == null ? 0 : this.loginDt.hashCode());
        result = 31 * result + (this.loginId == null ? 0 : this.loginId.hashCode());
        result = 31 * result + (this.loginPw == null ? 0 : this.loginPw.hashCode());
        result = 31 * result + (this.logoutDt == null ? 0 : this.logoutDt.hashCode());
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
        BookUser other = (BookUser)obj;
        if (this.entDt == null ? other.entDt != null : !this.entDt.equals(other.entDt)) {
            return false;
        }
        if (this.loginDt == null ? other.loginDt != null : !this.loginDt.equals(other.loginDt)) {
            return false;
        }
        if (this.loginId == null ? other.loginId != null : !this.loginId.equals(other.loginId)) {
            return false;
        }
        if (this.loginPw == null ? other.loginPw != null : !this.loginPw.equals(other.loginPw)) {
            return false;
        }
        if (this.logoutDt == null ? other.logoutDt != null : !this.logoutDt.equals(other.logoutDt)) {
            return false;
        }
        return true;
    }
}
