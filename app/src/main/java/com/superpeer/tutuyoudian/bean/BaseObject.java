package com.superpeer.tutuyoudian.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/10/10 0010.
 */

public class BaseObject implements Serializable {

    private String title;
    private String content;
    private String pubdate;
    private String agreementContent;
    private String createTime;
    private String roleType;
    private String payStatus;
    private String orderType;
    private String remainingTime;

    private String shopId;
    private String state;
    private String name;
    private String imgePath;
    private String type;
    private String typeName;
    private String businessScope;
    private String provinces;
    private String city;
    private String area;
    private String areaCode;
    private String longitude;
    private String latitude;
    private String address;
    private String bossName;
    private String phone;
    private String operatingStatus;
    private String openingTime;
    private String closingTime;
    private String shopStatus;
    private String username;
    private String invitationCode;
    private String activationStatus;
    private String activationFee;
    private String payType;
    private String shopDayOff;
    private String auditStatus;
    private String auditResult;
    private String auditTime;
    private String idCardImg;
    private String businessLicense;
    private String automaticStatus;
    private String foodBusinessLicense;
    private String accountId;
    private String sendStatus;
    private String runnerType;

    private String remark;

    //版本更新
    private String versionNumber;
    private String versionName;
    private String versionSrc;
    private String versionDescribe;

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionSrc() {
        return versionSrc;
    }

    public void setVersionSrc(String versionSrc) {
        this.versionSrc = versionSrc;
    }

    public String getVersionDescribe() {
        return versionDescribe;
    }

    public void setVersionDescribe(String versionDescribe) {
        this.versionDescribe = versionDescribe;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRunnerType() {
        return runnerType;
    }

    public void setRunnerType(String runnerType) {
        this.runnerType = runnerType;
    }

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
    }

    //骑手
    private String loginName;
    private String id;
    private String frontIdentity;
    private String reverseIdentity;
    private String userName;
    private String identityCard;
    private String balanceMoney;
    private String freezeMoney;
    private String runnerStatus;
    private String runnerShopRelation;

    //支付订单信息
    private String appid;
    private String sign;
    private String paytime;
    private String partnerid;
    private String prepayid;
    private String noncestr;
    private String timestamp;
    private String vipPrice;

    public String getRunnerShopRelation() {
        return runnerShopRelation;
    }

    public void setRunnerShopRelation(String runnerShopRelation) {
        this.runnerShopRelation = runnerShopRelation;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getRunnerStatus() {
        return runnerStatus;
    }

    public void setRunnerStatus(String runnerStatus) {
        this.runnerStatus = runnerStatus;
    }

    public String getFreezeMoney() {
        return freezeMoney;
    }

    public void setFreezeMoney(String freezeMoney) {
        this.freezeMoney = freezeMoney;
    }

    public String getBalanceMoney() {
        return balanceMoney;
    }

    public void setBalanceMoney(String balanceMoney) {
        this.balanceMoney = balanceMoney;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getFrontIdentity() {
        return frontIdentity;
    }

    public void setFrontIdentity(String frontIdentity) {
        this.frontIdentity = frontIdentity;
    }

    public String getReverseIdentity() {
        return reverseIdentity;
    }

    public void setReverseIdentity(String reverseIdentity) {
        this.reverseIdentity = reverseIdentity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    private List<GoodsVos> goodsVos;

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public List<GoodsVos> getGoodsVos() {
        return goodsVos;
    }

    public void setGoodsVos(List<GoodsVos> goodsVos) {
        this.goodsVos = goodsVos;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    private String orderNum;


    private String accountBalance;
    private String availableBalance;
    private String accountName;
    private String accountPhone;
    private String accountType;

    //首页
    private String todayMoney;
    private String yesterdayMoney;
    private String todayOrderNum;
    private String yesterdayOrderNum;
    private String todayBrowseNum;
    private String yesterdayBrowseNum;
    private String noRead;
    private String validityPeriod;

    //拼团详情
    private String shopName;
    private String goodsName;
    private String groupDesc;
    private String goodsPrice;
    private String groupPrice;
    private String goodsNum;
    private String goodsOriginalPrice;
    private String needNum;
    private String joinNum;
    private String shippingType;
    private String shippingTime;
    private String totalPrice;
    private String realPrice;
    private String price;
    private String originalPrice;
    private String groupNum;
    private String keepHour;
    private String cancelHour;
    private String noGetHour;
    private String noSendHour;

    private String imagePath;
    private String brand;
    private String specifications;
    private String unit;
    private String stock;
    private String preferentialPrice;
    private String saleState;
    private String bankId;
    private String manufacturer;

    //配送信息
    private String deliveryRange;
    private String minMonery;
    private String packingFee;
    private String deliverFee;
    private String deliveryTime;

    private String goodsId;

    //订单详情
    private String orderStatus;
    private String freight;
    private String successFlag; //成团标识(0:未成团，1：成团)

    private String consignee;
    private String discountDesc;
    private String cancelTime;

    public String getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(String validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getRealPrice() {
        return realPrice;
    }

    public void setRealPrice(String realPrice) {
        this.realPrice = realPrice;
    }

    public String getDiscountDesc() {
        return discountDesc;
    }

    public void setDiscountDesc(String discountDesc) {
        this.discountDesc = discountDesc;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(String joinNum) {
        this.joinNum = joinNum;
    }

    public String getShippingTime() {
        return shippingTime;
    }

    public void setShippingTime(String shippingTime) {
        this.shippingTime = shippingTime;
    }

    public String getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(String successFlag) {
        this.successFlag = successFlag;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(String goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(String groupPrice) {
        this.groupPrice = groupPrice;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getDeliveryRange() {
        return deliveryRange;
    }

    public void setDeliveryRange(String deliveryRange) {
        this.deliveryRange = deliveryRange;
    }

    public String getMinMonery() {
        return minMonery;
    }

    public void setMinMonery(String minMonery) {
        this.minMonery = minMonery;
    }

    public String getPackingFee() {
        return packingFee;
    }

    public void setPackingFee(String packingFee) {
        this.packingFee = packingFee;
    }

    public String getDeliverFee() {
        return deliverFee;
    }

    public void setDeliverFee(String deliverFee) {
        this.deliverFee = deliverFee;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSpecifications() {
        return specifications;
    }

    public void setSpecifications(String specifications) {
        this.specifications = specifications;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPreferentialPrice() {
        return preferentialPrice;
    }

    public void setPreferentialPrice(String preferentialPrice) {
        this.preferentialPrice = preferentialPrice;
    }

    public String getSaleState() {
        return saleState;
    }

    public void setSaleState(String saleState) {
        this.saleState = saleState;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNoRead() {
        return noRead;
    }

    public void setNoRead(String noRead) {
        this.noRead = noRead;
    }

    public String getKeepHour() {
        return keepHour;
    }

    public void setKeepHour(String keepHour) {
        this.keepHour = keepHour;
    }

    public String getCancelHour() {
        return cancelHour;
    }

    public void setCancelHour(String cancelHour) {
        this.cancelHour = cancelHour;
    }

    public String getNoGetHour() {
        return noGetHour;
    }

    public void setNoGetHour(String noGetHour) {
        this.noGetHour = noGetHour;
    }

    public String getNoSendHour() {
        return noSendHour;
    }

    public void setNoSendHour(String noSendHour) {
        this.noSendHour = noSendHour;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsOriginalPrice() {
        return goodsOriginalPrice;
    }

    public void setGoodsOriginalPrice(String goodsOriginalPrice) {
        this.goodsOriginalPrice = goodsOriginalPrice;
    }

    public String getNeedNum() {
        return needNum;
    }

    public void setNeedNum(String needNum) {
        this.needNum = needNum;
    }

    public String getShippingType() {
        return shippingType;
    }

    public void setShippingType(String shippingType) {
        this.shippingType = shippingType;
    }

    public String getTodayMoney() {
        return todayMoney;
    }

    public void setTodayMoney(String todayMoney) {
        this.todayMoney = todayMoney;
    }

    public String getYesterdayMoney() {
        return yesterdayMoney;
    }

    public void setYesterdayMoney(String yesterdayMoney) {
        this.yesterdayMoney = yesterdayMoney;
    }

    public String getTodayOrderNum() {
        return todayOrderNum;
    }

    public void setTodayOrderNum(String todayOrderNum) {
        this.todayOrderNum = todayOrderNum;
    }

    public String getYesterdayOrderNum() {
        return yesterdayOrderNum;
    }

    public void setYesterdayOrderNum(String yesterdayOrderNum) {
        this.yesterdayOrderNum = yesterdayOrderNum;
    }

    public String getTodayBrowseNum() {
        return todayBrowseNum;
    }

    public void setTodayBrowseNum(String todayBrowseNum) {
        this.todayBrowseNum = todayBrowseNum;
    }

    public String getYesterdayBrowseNum() {
        return yesterdayBrowseNum;
    }

    public void setYesterdayBrowseNum(String yesterdayBrowseNum) {
        this.yesterdayBrowseNum = yesterdayBrowseNum;
    }

    public String getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPubdate() {
        return pubdate;
    }

    public void setPubdate(String pubdate) {
        this.pubdate = pubdate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAgreementContent() {
        return agreementContent;
    }

    public void setAgreementContent(String agreementContent) {
        this.agreementContent = agreementContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgePath() {
        return imgePath;
    }

    public void setImgePath(String imgePath) {
        this.imgePath = imgePath;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBossName() {
        return bossName;
    }

    public void setBossName(String bossName) {
        this.bossName = bossName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOperatingStatus() {
        return operatingStatus;
    }

    public void setOperatingStatus(String operatingStatus) {
        this.operatingStatus = operatingStatus;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public String getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(String shopStatus) {
        this.shopStatus = shopStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getActivationStatus() {
        return activationStatus;
    }

    public void setActivationStatus(String activationStatus) {
        this.activationStatus = activationStatus;
    }

    public String getActivationFee() {
        return activationFee;
    }

    public void setActivationFee(String activationFee) {
        this.activationFee = activationFee;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getShopDayOff() {
        return shopDayOff;
    }

    public void setShopDayOff(String shopDayOff) {
        this.shopDayOff = shopDayOff;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAutomaticStatus() {
        return automaticStatus;
    }

    public void setAutomaticStatus(String automaticStatus) {
        this.automaticStatus = automaticStatus;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(String auditTime) {
        this.auditTime = auditTime;
    }

    public String getIdCardImg() {
        return idCardImg;
    }

    public void setIdCardImg(String idCardImg) {
        this.idCardImg = idCardImg;
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getFoodBusinessLicense() {
        return foodBusinessLicense;
    }

    public void setFoodBusinessLicense(String foodBusinessLicense) {
        this.foodBusinessLicense = foodBusinessLicense;
    }
}
