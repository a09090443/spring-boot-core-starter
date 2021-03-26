package com.zipe.employee.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : Gary Tsai
 * @created : @Date 2020/12/21 下午 03:12
 **/
@Data
@Entity
@Table
public class Personnel implements Serializable {

    @Column(name = "PN_EMPUUID")
    String pnEmpUUID;
    @Column(name = "CPO_CODE")
    String cpoCode;
    @Column(name = "DP_CODE")
    String dpCode;
    @Column(name = "AS_TYPE")
    String asType;
    @Column(name = "SYSSTATUS")
    String sysStatus;
    @Column(name = "SYSCREATOR")
    String sysCreator;
    @Column(name = "SYSCREATETIME")
    LocalDateTime sysCreateTime;
    @Column(name = "SYSMODIFIER")
    String sysModifier;
    @Column(name = "SYSMODIFYTIME")
    LocalDateTime sysModifyTime;
    @Id
    @Column(name = "PN_EMPNO")
    String pnEmpNo;
    @Column(name = "PN_IDNOTYPE")
    String pnIdNoType;
    @Column(name = "PN_IDNO")
    String pnIdNo;
    @Column(name = "PN_CHINESENAME")
    String pnChineseName;
    @Column(name = "PN_ENGLISHNAME")
    String pnEnglishName;
    @Column(name = "PN_DEPTCODE")
    String pnDeptCode;
    @Column(name = "PN_STATUS")
    String pnStatus;
    @Column(name = "PN_HIRETYPE")
    String pnHireType;
    @Column(name = "PN_FIRSTWORKDATE")
    LocalDateTime pnFirstWorkDate;
    @Column(name = "PN_HIREDATE")
    LocalDateTime pnHireDate;
    @Column(name = "PN_POSTDATE")
    LocalDateTime pnPostDate;
    @Column(name = "PN_STARTDATE")
    LocalDateTime pnStartDate;
    @Column(name = "PN_WORKYEAR")
    String pnWorkYear;
    @Column(name = "PN_RETIREMONEYRECORD")
    String pnRetireMoneyRecord;
    @Column(name = "PN_SUBSTITUTEFLAG")
    String pnSubsTitleFlag;
    @Column(name = "PN_WELFAREFLAG")
    String pnWelfareFlag;
    @Column(name = "PN_SEX")
    String pnSex;
    @Column(name = "PN_BLOODTYPE")
    String pnBloodType;
    @Column(name = "PN_FEEDPEOPLE")
    Long pnFeedPeople;
    @Column(name = "PN_BIRTHDAY")
    LocalDateTime pnBirthday;
    @Column(name = "PN_MILITARYSERVICE")
    String pnMilitaryService;
    @Column(name = "PN_RECRUITSOURCE")
    String pnRecruitSource;
    @Column(name = "PN_RECRUITTYPE")
    String pnRecruitType;
    @Column(name = "PN_BIRTHLOCATION")
    String pnBirthLocation;
    @Column(name = "PN_FATHERNAME")
    String pnFatherName;
    @Column(name = "PN_MOTHERNAME")
    String pnMotherName;
    @Column(name = "PN_MATENAME")
    String pnMateName;
    @Column(name = "PN_INTRODUCERIDNO")
    String pnIntroducerIdNo;
    @Column(name = "PN_INTRODUCERNAME")
    String pnIntroducerName;
    @Column(name = "PN_LIVEADDRZIPCODE")
    String pnLiveAddrZipCode;
    @Column(name = "PN_LIVEADDR")
    String pnLiveAddr;
    @Column(name = "PN_REGADDRZIPCODE")
    String pnRegAddrZipCode;
    @Column(name = "PN_REGADDR")
    String pnRegAddr;
    @Column(name = "PN_LIVEPHONENO")
    String pnLivePhoneNo;
    @Column(name = "PN_REGPHONENO")
    String pnRegPhoneNo;
    @Column(name = "PN_LIVEPHONEZIPNO")
    String pnLivePhoneZipNo;
    @Column(name = "PN_REGPHONEZIPNO")
    String pnRegPhoneZipNo;
    @Column(name = "PN_EMAIL1")
    String pnEmail1;
    @Column(name = "PN_EMAIL2")
    String pnEmail2;
    @Column(name = "PN_MOBILENO1")
    String pnMobileNo1;
    @Column(name = "PN_MOBILENO2")
    String pnMobileNo2;
    @Column(name = "PN_SALARYBANKCODE")
    String pnSalaryBankCode;
    @Column(name = "PN_SALARYBRANCHCODE")
    String pnSalaryBranchCode;
    @Column(name = "PN_SALARYACCOUNT")
    String pnSalaryAccount;
    @Column(name = "PN_CONTACTPERSONNAME")
    String pnContactPersonName;
    @Column(name = "PN_CONTACTPERSONMOBILENO")
    String pnContacPersonMobileNo;
    @Column(name = "PN_CONTACTPERSONPHONEZIPNO")
    String pnContactPersonPhoneZipNo;
    @Column(name = "PN_CONTACTPERSONPHONENO")
    String pnContacPersonPhoneNo;
    @Column(name = "PN_CONTACTPERSONRELATION")
    String pnContactPersonRelation;
    @Column(name = "PN_FORMALEMP")
    String pnFormalEmp;
    @Column(name = "PN_MANAGEDUTY")
    String pnManageDuty;
    @Column(name = "PN_OFFICEPHONEEXT")
    String pnOfficePhoneExt;
    @Column(name = "PN_LABORATTENDTYPE")
    String pnLaborattEndtype;
    @Column(name = "PN_REDUCETYPE1")
    String pnReduceType1;
    @Column(name = "PN_REDUCETYPE2")
    String pnReduceType2;
    @Column(name = "PN_OVERSEAS")
    String pnOverSeas;
    @Column(name = "PN_CREDITCARD")
    String pnCreditCard;
    @Column(name = "PN_CREDITCARD2")
    String pnCreditCard2;
    @Column(name = "PN_REMARK")
    String pnRemark;
    @Column(name = "PN_HASDONE")
    String pnHasDone;
    @Column(name = "PN_NEXTANNUALLEAVE")
    LocalDateTime pnNextAnnualLeave;
    @Column(name = "PN_JOBSUSPENDMONTH")
    String pnJobSuspendMonth;
    @Column(name = "PN_LABORSUBSIDYTYPE")
    String pnLaboSubsidyType;
    @Column(name = "PN_ANNUALLEAVEMONTH")
    Long pnAnnualLeaveMonth;
    @Column(name = "PN_YEARSTARTJOBDATE")
    LocalDateTime pnYearStartJobDate;
    @Column(name = "PN_YEARQUITJOBDATE")
    LocalDateTime pnYearQuitJobDate;
    @Column(name = "PN_EXEMPNO")
    String pnExEmpNo;
    @Column(name = "PN_LABORREDUCETYPE")
    String pnLaborReduceType;
    @Column(name = "PN_SALARYPWD")
    String pnSalaryPwd;
    @Column(name = "PN_TAXDEDUCT")
    Long pnTaxDeduct;
    @Column(name = "PN_COSTCENTER")
    String pnCostCenter;
    @Column(name = "PN_BREAKDAYS")
    Long pnBreakDays;
    @Column(name = "PN_BREAKYM")
    String pnBreakYM;
    @Column(name = "PN_PHOTO")
    byte[] pnPhoto;
    @Column(name = "PN_MARRIAGESTATUS")
    String pnMarriageStatus;
    @Column(name = "PN_PAYMENT")
    String pnPayment;
    @Column(name = "PN_PAYTARGETID")
    String pnPayTargetId;
    @Column(name = "PN_WORKPLACE")
    String pnWorkPlace;
    @Column(name = "CPT_CODE")
    String cptCode;
    @Column(name = "PN_NATION")
    String pnNation;
    @Column(name = "SUB_POST")
    String subPost;

}
