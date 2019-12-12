var userLang = document.getElementsByTagName("html")[0].getAttribute("lang");

var errorMessagesLanguage =
    [
        "Please enter а valid email address.",
        "Пожалуйста введите правильную эл. почту.",
        "Калі ласка увядзіце правільную эл. пошту."
    ];

function getTranslatedErrorMessage(index) {
    var errorMessage;
    if (userLang === "en_US") {
        errorMessage = errorMessagesLanguage[index];
    } else if (userLang === "ru_RU") {
        errorMessage = errorMessagesLanguage[index + 1];
    } else if (userLang === "be_BY") {
        errorMessage = errorMessagesLanguage[index + 2];
    } else {
        errorMessage = errorMessagesLanguage[index];
    }
    return errorMessage;
}


function printError(elemId, hintMsg) {
    document.getElementById(elemId).innerHTML = hintMsg;
}

function validateEmail(email, emailErrorId, indexErrorMessage) {
    if (email === "") {
        printError(emailErrorId, getTranslatedErrorMessage(indexErrorMessage));
    } else {
        var regex = /^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/;
        if (regex.test(email) === false) {
            printError(emailErrorId, getTranslatedErrorMessage(indexErrorMessage));
        } else {
            printError(emailErrorId, "");
        }
    }
}

function validateForm() {
    var adminEmail = document.forms["contactForm"]["adminEmail"].value;
    var enrolleeEmail = document.forms["contactForm"]["enrolleeEmail"].value;
    validateEmail(adminEmail, "adminEmailError", 0);
    validateEmail(enrolleeEmail, "enrolleeEmailError", 0);
}