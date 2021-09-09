<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html lang="${language}">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/register_style.css"/>">
    <script src="<c:url value="/resources/js/jquery-3.4.1.js"/>"></script>
    <link rel="stylesheet" href="<c:url value="/resources/select2-4.0.10/css/select2.min.css"/>">
    <script src="<c:url value="/resources/select2-4.0.10/js/select2.min.js"/>"></script>
    <script src="<c:url value="/resources/js/fileinput.js"/>" charset="UTF-8"></script>
    <script src="<c:url value="/resources/js/ready.js"/>" charset="UTF-8"></script>
    <title></title>
</head>
<body>
<div class="form-style">
    <form name="registerCompititionForm" enctype="multipart/form-data" method="POST" action="controller">
        <input type="hidden" name="command" value="compitition"/>
        <fieldset class="fieldset-first">
            <legend><span class="number">1</span> <fmt:message key="register.page.faculty_speciality"/></legend>
            <div class="faculties-div">
                <h4><fmt:message key="register.page.faculty"/></h4>
                <label>
                    <select id="faculties" class="select2-faculties" name="department" onChange="changeDepartmentValues(this.selectedIndex)">
                        <option><fmt:message key="faculty.bio"/></option>
                        <option><fmt:message key="faculty.his"/></option>
                        <option><fmt:message key="faculty.ecolog"/></option>
                        <option><fmt:message key="faculty.mmf"/></option>
                        <option><fmt:message key="faculty.geo"/></option>
                        <option><fmt:message key="faculty.journ"/></option>
                        <option><fmt:message key="faculty.inter_relationship"/></option>
                        <option><fmt:message key="faculty.fami"/></option>
                        <option><fmt:message key="faculty.radiophys"/></option>
                        <option><fmt:message key="faculty.philo_social"/></option>
                        <option><fmt:message key="faculty.physics"/></option>
                        <option><fmt:message key="faculty.philol"/></option>
                        <option><fmt:message key="faculty.chem"/></option>
                        <option><fmt:message key="faculty.econom"/></option>
                        <option><fmt:message key="faculty.law"/></option>
                    </select>
                </label>
            </div>
            <div class="specialities-div">
                <h4><fmt:message key="register.page.speciality"/></h4>
                <label>
                    <select id="specialities" class="select2-specialities" name="speciality">
                        <option value="N/A">N/A</option>
                    </select>
                </label>
            </div>
            <script src="<c:url value="/resources/js/department-speciality.js"/>" charset="UTF-8"></script>
        </fieldset>
        <fieldset>
            <legend><span class="number">2</span> <fmt:message key="register.page.empl_info"/></legend>
            <h4><fmt:message key="register.page.birthday"/></h4>
            <div class="birthday">
                <label>
                    <input id="month" required type="text" name="month" placeholder="<fmt:message key="register.page.birth_month"/>"/>
                </label>
                <label class="sec-label-birth">
                    <input id="day" required type="text" name="day" placeholder="<fmt:message key="register.page.birth_day"/>"/>
                </label>
                <label>
                    <input id="year" required type="text" name="year" placeholder="<fmt:message key="register.page.birth_year"/>"/>
                </label>
            </div>
            <h4><fmt:message key="register.page.address_info"/></h4>
            <div class="address">
                <div class="row">
                    <div class="left-column-enrollee">
                        <label for="district"><fmt:message key="register.page.district"/></label>
                    </div>
                    <div class="right-column-enrollee">
                        <input id="district" required type="text" name="district" placeholder="<fmt:message key="register.page.placeholder.district"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column-enrollee">
                        <label for="locality"><fmt:message key="register.page.locality"/></label>
                    </div>
                    <div class="right-column-enrollee">
                        <input id="locality" required type="text" name="locality" placeholder="<fmt:message key="register.page.placeholder.locality"/>"/>
                    </div>
                </div>
            </div>
            <h4><fmt:message key="register.page.avatar"/></h4>
            <div class="fileUp">
                <input id="file-input" required type="file" name="file" multiple/>
            </div>
        </fieldset>
        <fieldset>
            <legend><span class="number">3</span> <fmt:message key="register.page.school_sub"/></legend>
            <div class="school-subjects">
                <div class="row">
                    <div class="left-column">
                        <label for="lang_bel"><fmt:message key="register.page.lang_bel"/></label>
                    </div>
                    <div class="right-column">
                        <input id="lang_bel" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column"><label for="lang_bel_lit">
                        <fmt:message key="register.page.lang_bel_lit"/></label>
                    </div>
                    <div class="right-column">
                        <input id="lang_bel_lit" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="lang_rus"><fmt:message key="register.page.lang_rus"/></label>
                    </div>
                    <div class="right-column">
                        <input id="lang_rus" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="lang_rus_lit"><fmt:message key="register.page.lang_rus_lit"/></label>
                    </div>
                    <div class="right-column">
                        <input id="lang_rus_lit" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="foreign_lang"><fmt:message key="register.page.foreign_lang"/></label>
                    </div>
                    <div class="right-column">
                        <input id="foreign_lang" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="maths"><fmt:message key="register.page.maths"/></label>
                    </div>
                    <div class="right-column">
                        <input id="maths" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="informatics"><fmt:message key="register.page.informatics"/></label>
                    </div>
                    <div class="right-column">
                        <input id="informatics" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="bel_his"><fmt:message key="register.page.bel_his"/></label>
                    </div>
                    <div class="right-column">
                        <input id="bel_his" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="world_his"><fmt:message key="register.page.world_his"/></label>
                    </div>
                    <div class="right-column">
                        <input id="world_his" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="social_science"><fmt:message key="register.page.social_science"/></label>
                    </div>
                    <div class="right-column">
                        <input id="social_science" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="geo_sub"><fmt:message key="faculty.geo_sub"/></label>
                    </div>
                    <div class="right-column">
                        <input id="geo_sub" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="bio_sub"><fmt:message key="faculty.bio_sub"/></label>
                    </div>
                    <div class="right-column">
                        <input id="bio_sub" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="phys_sub"><fmt:message key="register.page.phys_sub"/></label>
                    </div>
                    <div class="right-column">
                        <input id="phys_sub" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="astro"><fmt:message key="register.page.astro"/></label>
                    </div>
                    <div class="right-column">
                        <input id="astro" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="chem"><fmt:message key="faculty.chem_school_sub"/></label>
                    </div>
                    <div class="right-column">
                        <input id="chem" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="training"><fmt:message key="register.page.training"/></label>
                    </div>
                    <div class="right-column">
                        <input id="training" required type="text" name="school_mark" value="" placeholder="<fmt:message key="register.page.placeholder.mark"/>"/>
                    </div>
                </div>
            </div>
        </fieldset>
        <fieldset>
            <legend><span class="number">4</span> <fmt:message key="register.page.exams"/></legend>
            <div class="examination">
                <div class="row">
                    <div class="left-column">
                        <label for="exam1"><fmt:message key="register.page.exam1"/></label>
                    </div>
                    <div class="right-column">
                        <input id="exam1" required type="text" name="exam1" value="" placeholder="<fmt:message key="register.page.placeholder.score"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="exam2"><fmt:message key="register.page.exam2"/></label>
                    </div>
                    <div class="right-column">
                        <input id="exam2" required type="text" name="exam2" value="" placeholder="<fmt:message key="register.page.placeholder.score"/>"/>
                    </div>
                </div>
                <div class="row">
                    <div class="left-column">
                        <label for="exam3"><fmt:message key="register.page.exam3"/></label>
                    </div>
                    <div class="right-column">
                        <input id="exam3" required type="text" name="exam3" value="" placeholder="<fmt:message key="register.page.placeholder.score"/>"/>
                    </div>
                </div>
            </div>
        </fieldset>
        <div class="container-register-form-btn">
            <input type="text" name="login" value="${requestScope.loginFromCompititionRegister}">
            <button class="register-form-btn"><fmt:message key="register.page.compitition.message"/></button>
        </div>
    </form>
</div>
</body>
</html>
