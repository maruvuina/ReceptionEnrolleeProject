let userLang = document.getElementsByTagName("html")[0].getAttribute("lang");

let errorMessagesLanguageForEmptyField =
    [
        "Please fill field.",
        "Пожалуйста заполните поле.",
        "Калі ласка запоўніце поле."
    ];

let errorEmailMessagesLanguage =
    [
        "Please enter а valid email address.",
        "Пожалуйста введите правильную эл. почту.",
        "Калі ласка увядзіце правільную эл. пошту."
    ];

let errorPasswordMessagesLanguage =
    [
        "Please enter a password.",
        "Пожалуйста введите правильный пароль.",
        "Калі ласка увядзіце правільны пароль."
    ];

let errorFullnameMessagesLanguage =
    [
        "Please verify your full name.",
        "Пожалуйста проверьте ваше ФИО.",
        "Калі ласка праверце ваша поўнае імя."
    ];

let errorBirthdayMessagesLanguage =
    [
        "Please verify your birthday date.",
        "Пожалуйста проверьте введенную дату рождения.",
        "Калі ласка праверце уведзеную дату нараджэння."
    ];

function getTranslatedErrorMessage(errorMessagesLanguageType, index) {
    let errorMessage;
    if (userLang === "en_US") {
        errorMessage = errorMessagesLanguageType[index];
    } else if (userLang === "ru_RU") {
        errorMessage = errorMessagesLanguageType[index + 1];
    } else if (userLang === "be_BY") {
        errorMessage = errorMessagesLanguageType[index + 2];
    } else {
        errorMessage = errorMessagesLanguageType[index];
    }
    return errorMessage;
}

function getTranslatedErrorMessageForConcreteField(errorMessageFieldType, indexErrorMessage, errorMessageType) {
    let errorMessage;
    let errorMessageConcreteFieldType;
    if (errorMessageFieldType === "emailErrorMessage") {
        errorMessageConcreteFieldType = errorEmailMessagesLanguage;
    } else if (errorMessageFieldType === "passwordErrorMessage") {
        errorMessageConcreteFieldType = errorPasswordMessagesLanguage;
    } else if (errorMessageFieldType === "fullNameErrorMessage") {
        errorMessageConcreteFieldType = errorFullnameMessagesLanguage;
    } else if (errorMessageFieldType === "birthdayErrorMessage") {
        errorMessageConcreteFieldType = errorBirthdayMessagesLanguage;
    }
    if (errorMessageType === 0) {
        errorMessage = getTranslatedErrorMessage(errorMessagesLanguageForEmptyField, indexErrorMessage);
    } else {
        errorMessage = getTranslatedErrorMessage(errorMessageConcreteFieldType, indexErrorMessage);
    }
    return errorMessage;
}

function validateStringField(string) {
    let regex = /^([А-Я][а-яё]{1,23}|[A-Z][a-z]{1,23})$/;
    return regex.test(string) !== false;
}

function validateDay(string) {
    let regex = /(0[1-9]|[12]\\d|3[01])/;
    return regex.test(string) !== false;
}

function validateMonth(string) {
    let regex = /^(0?[1-9]|1[012])$/;
    return regex.test(string) !== false;
}

function validateYear(string) {
    let regex = /^\\d{4}$/;
    return regex.test(string) !== false;
}

function printError(elemId, hintMsg) {
    let errrorDiv = document.getElementById(elemId);
    errrorDiv.innerHTML = hintMsg;
    errrorDiv.style.background = "#ffd2d2";
}

function validateBirthday(day, month, year) {
    let stringErrorName = "errorBirthday";
    if(!validateDay(day) || !validateMonth(month) || !validateYear(year)) {
        printError(stringErrorName, getTranslatedErrorMessageForConcreteField("birthdayErrorMessage", 0, 1));
    } else {
        printError(stringErrorName, "");
    }
}

function validateFullName(firstName, lastName, middleName) {
    let stringErrorName = "errorFullname";
    if(!validateStringField(firstName) || !validateStringField(lastName) || !validateStringField(middleName)) {
        printError(stringErrorName, getTranslatedErrorMessageForConcreteField("fullNameErrorMessage", 0, 1));
    } else {
        printError(stringErrorName, "");
    }
}

function validateForm() {
    let firstName = document.forms["editForm"]["firstNameToChange"].value;
    let lastName = document.forms["editForm"]["lastNameToChange"].value;
    let middleName = document.forms["editForm"]["middleNameToChange"].value;
    let day = document.forms["editForm"]["day"].value;
    let month = document.forms["editForm"]["month"].value;
    let year = document.forms["editForm"]["year"].value;
    validateFullName(firstName, lastName, middleName);
    validateBirthday(day, month, year);
}

function setChoosingInformation() {
    let checkbox1 = document.getElementById('checkbox-1');
    let checkbox2 = document.getElementById('checkbox-2');
    let changeCase = document.getElementById('changeCase');
    if (checkbox1.checked && checkbox2.checked) {
        changeCase.value = "2";
    } else if (checkbox2.checked) {
        changeCase.value = "1";
    } else if (checkbox1.checked) {
        changeCase.value = "0";
    } else {
        changeCase.value = "";
    }
}

let attempt = document.getElementById('attempt');
if (attempt.innerHTML === "0") {
    let checkbox2 = document.getElementById('checkbox-2');
    checkbox2.disabled = true;
    document.getElementById('label-checkbox-2').style.background = "grey";
}