var userLang = document.getElementsByTagName("html")[0].getAttribute("lang");

var errorMessagesLanguageForEmptyField =
    [
        "Please fill field.",
        "Пожалуйста заполните поле.",
        "Калі ласка запоўніце поле."
    ];

var errorEmailMessagesLanguage =
    [
        "Please enter а valid email address.",
        "Пожалуйста введите правильную эл. почту.",
        "Калі ласка увядзіце правільную эл. пошту."
    ];

var errorPasswordMessagesLanguage =
    [
        "Please enter a password.",
        "Пожалуйста введите правильный пароль.",
        "Калі ласка увядзіце правільны пароль."
    ];

var errorStringMessagesLanguage =
    [
        "Please verify your full name.",
        "Пожалуйста проверьте ваше ФИО.",
        "Калі ласка праверце ваша поўнае імя."
    ];

function getTranslatedErrorMessage(errorMessagesLanguageType, index) {
    var errorMessage;
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
    var errorMessage;
    var errorMessageConcreteFieldType;
    if (errorMessageFieldType === "emailErrorMessage") {
        errorMessageConcreteFieldType = errorEmailMessagesLanguage;
    } else if (errorMessageFieldType === "passwordErrorMessage") {
        errorMessageConcreteFieldType = errorPasswordMessagesLanguage;
    } else if (errorMessageFieldType === "stringErrorMessage") {
        errorMessageConcreteFieldType = errorStringMessagesLanguage;
    }
    if (errorMessageType === 0) {
        errorMessage = getTranslatedErrorMessage(errorMessagesLanguageForEmptyField, indexErrorMessage);
    } else {
        errorMessage = getTranslatedErrorMessage(errorMessageConcreteFieldType, indexErrorMessage);
    }
    return errorMessage;
}

function printError(elemId, hintMsg) {
    var errrorDiv = document.getElementById(elemId);
    errrorDiv.innerHTML = hintMsg;
    errrorDiv.style.background = "#ffd2d2";
}

function validateEmail(email, emailErrorId, indexErrorMessage) {
    if(email === "") {
        printError(emailErrorId, getTranslatedErrorMessageForConcreteField("emailErrorMessage", indexErrorMessage, 0));
    } else {
        var regex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
        if(regex.test(email) === false) {
            printError(emailErrorId, getTranslatedErrorMessageForConcreteField("emailErrorMessage", indexErrorMessage, 1));
        } else {
            printError(emailErrorId, "");
        }
    }
}

function validatePassword(password, passwordErrorId, indexErrorMessage) {
    if(password === "") {
        printError(passwordErrorId, getTranslatedErrorMessageForConcreteField("passwordErrorMessage", indexErrorMessage, 0));
    } else {
        var regex = /(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\S+$).{8,}/;
        if(regex.test(password) === false) {
            printError(passwordErrorId, getTranslatedErrorMessageForConcreteField("passwordlErrorMessage", indexErrorMessage, 1));
        } else {
            printError(passwordErrorId, "");
        }
    }
}

function validateStringField(string) {
    var regex = /^([А-Я][а-яё]{1,23}|[A-Z][a-z]{1,23})$/;
    return regex.test(string) !== false;
}

function validateFullName(firstName, lastName, middleName) {
    var stringErrorName = "errorFullname";
    if(!validateStringField(firstName) || !validateStringField(lastName) || !validateStringField(middleName)) {
        printError(stringErrorName, getTranslatedErrorMessageForConcreteField("stringErrorMessage", 0, 1));
    } else {
        printError(stringErrorName, "");
    }
}

function validateForm() {
    var email = document.forms["registerForm"]["login"].value;
    var password = document.forms["registerForm"]["password"].value;
    var firstName = document.forms["registerForm"]["completename"].value;
    var lastName = document.forms["registerForm"]["surname"].value;
    var middleName = document.forms["registerForm"]["middlename"].value;
    validateEmail(email, "emailError", 0);
    validatePassword(password, "passwordError", 0);
    validateFullName(firstName, lastName, middleName);
}