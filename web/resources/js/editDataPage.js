var userLang = document.getElementsByTagName("html")[0].getAttribute("lang");

var specialityValuesEnglish =
    [
        "biology (study field - biotechnology)," +
        "biology (study field - scientific and pedagogical work)," +
        "biology (study field - scientific and production work)," +
        "biochemistry," +
        "bio-ecology," +
        "microbiology",

        "records management (by study fields)," +
        "historical archival management," +
        "history (by study fields)," +
        "museum business and protection of historical and cultural heritage (by study fields)",

        "information systems and technologies (in public health)," +
        "information systems and technologies (in ecology)," +
        "biomedical work," +
        "medical ecology," +
        "medical physics" +
        "environmental work (by study fields)," +
        "energy efficient technologies and energy management," +
        "nuclear and radiation safety",

        "computer mathematics and systems analysis," +
        "mathematics (study field - scientific and engineering work)," +
        "mathematics (study field - scientific and pedagogical work)," +
        "mathematics (study field - scientific and production work)," +
        "mathematics (study field - economic work)," +
        "mathematics and information technologies (study field - web-programming and internet-technologies)," +
        "mathematics and information technologies (study field - mathematical and software mobile devices)," +
        "mechanics and mathematical modeling," +
        "mechanics and mathematical modeling - co-education BSU and DPU",

        "geography (by study fields)," +
        "geo-information systems (study field - land-cadastral)," +
        "geology and mineral exploration," +
        "geo-ecology," +
        "hydrometeorology," +
        "space aerocartography",

        "information and communication",

        "country studies through language," +
        "international law," +
        "international relationships," +
        "management (study field - management in the field of international tourism)," +
        "world economy," +
        "customs business",

        "actuarial mathematics," +
        "informatics," +
        "computer security (study field - mathematical methods and software systems)," +
        "applied informatics (study field - software of computer systems)," +
        "applied mathematics (study field - scientific and production work)," +
        "economic cybernetics (study field - mathematical methods and computer modeling in economics)",

        "aerospace radio-electronic and information systems and technologies," +
        "computer security (study field - radiophysical methods and software and hardware)," +
        "applied informatics (study field - information technologies of telecommunication systems)," +
        "radiophysics," +
        "physical electronics",

        "psychology," +
        "social work (by study fields)," +
        "social communications," +
        "sociology," +
        "philosophy",

        "computer physics," +
        "physics (study field - scientific and research work)," +
        "physics (study field - production work)," +
        "physics (study field - production work) - co-education BSU and DPU," +
        "physics of nanomaterials and nanotechnologies," +
        "nuclear physics and technology",

        "romance-germanic (English) philology," +
        "eastern (Chinese) philology," +
        "romance-germanic (Italian) philology," +
        "romance-germanic (German) philology," +
        "romance-germanic (French) philology",

        "fundamental chemistry," +
        "chemistry (study field - scientific and pedagogical work)," +
        "chemistry (study field - scientific and production work)," +
        "chemistry (study field - pharmaceutical work)," +
        "high energy chemistry," +
        "chemistry of medicinal compounds",

        "management (study field - international management," +
        "finance and credit," +
        "economy," +
        "economic informatics," +
        "economic theory",

        "political science (study field - political and legal work)," +
        "jurisprudence," +
        "economic law"
    ];

var specialityValuesRussian =
    [
        "биология (направление - биотехнология)," +
        "биология (направление - научно-педагогическая деятельность)," +
        "биология (направление - научно-производственная деятельность)," +
        "биохимия," +
        "биоэкология," +
        "микробиология",

        "документоведение (по направлениям)," +
        "историко-архивоведение," +
        "история (по направлениям)," +
        "музейное дело и охрана историко-культурного наследия (по направлениям)",

        "информационные системы и технологии (в здравоохранении)," +
        "информационные системы и технологии (в экологии)," +
        "медико-биологическое дело," +
        "медицинская экология," +
        "медицинская физика," +
        "природоохранная деятельность (по направлениям)," +
        "энергоэффективные технологии и энергетический менеджмент," +
        "ядерная и радиационная безопасность",

        "компьютерная математика и системный анализ," +
        "математика (направление - научно-конструкторская деятельность)," +
        "математика (направление - научно-педагогическая деятельность)," +
        "математика (направление - научно-производственная деятельность)," +
        "математика (направление - экономическая деятельность)," +
        "математика и информационные технологии (направление - веб-программирование и интернет-технологии)," +
        "математика и информационные технологии (направление - математическое и программное обеспечение мобильных устройств)," +
        "механика и математическое моделирование," +
        "механика и математическое моделирование - совместная программа БГУ и ДПУ",

        "география (по направлениям)," +
        "геоинформационные системы (направление - земельно-кадастровые)," +
        "геология и разведка месторождений полезных ископаемых," +
        "геоэкология," +
        "гидрометеорология," +
        "космоаэрокартография",

        "информация и коммуникация",

        "лингвострановедение," +
        "международное право," +
        "международные отношения," +
        "менеджмент (направление - менеджмент в сфере международного туризма)," +
        "мировая экономика," +
        "таможенное дело",

        "актуарная математика," +
        "информатика," +
        "компьютерная безопасность (направление - математические методы и программные системы)," +
        "прикладная информатика (направление - программное обеспечение компьютерных систем)," +
        "прикладная математика (направление - научно-производственная деятельность)," +
        "экономическая кибернетика (направление - математические методы и компьютерное моделирование в экономике)",

        "аэрокосмические радиоэлектронные и информационные системы и технологии," +
        "компьютерная безопасность (направление - радиофизические методы и программно-технические средства)," +
        "прикладная информатика (направление - информационные технологии телекоммуникационных систем)," +
        "радиофизика," +
        "физическая электроника",

        "психология," +
        "социальная работа (по направлениям)," +
        "социальные коммуникации," +
        "социология," +
        "философия",

        "компьютерная физика," +
        "физика (направление - научно-исследовательская деятельность)," +
        "физика (направление - производственная деятельность)," +
        "физика (направление - производственная деятельность) - совместная программа БГУ и ДПУ," +
        "физика наноматериалов и нанотехнологий," +
        "ядерные физика и технологии",

        "романо-германская (английская) филология," +
        "восточная (китайская) филология," +
        "романо-германская (итальянская) филология," +
        "романо-германская (немецкая) филология," +
        "романо-германская (французская) филология",

        "фундаментальная химия," +
        "химия (направление - научно-педагогическая деятельность)," +
        "химия (направление - научно-производственная деятельность)," +
        "химия (направление - фармацевтическая деятельность)," +
        "химия высоких энергий," +
        "химия лекарственных соединений",

        "менеджмент (направления - международный менеджмент)," +
        "финансы и кредит," +
        "экономика," +
        "экономическая информатика," +
        "экономическая теория",

        "политология (направление - политико-юридическая деятельность)," +
        "правоведение," +
        "экономическое право"
    ];

var specialityValuesBelarussian =
    [
        "біялогія (напрамак - біятэхналогія)," +
        "біялогія (напрамак - навукова-педагагічная дзейнасць)," +
        "біялогія (напрамак - навукова-вытворчая дзейнасць)," +
        "біяхімія," +
        "біяэкалогія," +
        "мікрабіялогія",

        "дакументазнаўства (па напрамках)," +
        "гісторыка-архівазнаўства," +
        "гісторыя (па напрамках)," +
        "музейная справа і ахова гісторыка-культурнай спадчыны (па напрамках)",

        "інфармацыйныя сістэмы і тэхналогіі (у ахове здароўя)," +
        "інфармацыйныя сістэмы і тэхналогіі (у экалогіі)," +
        "медыка-біялагічнае справа," +
        "медыцынская экалогія," +
        "медыцынская фізіка," +
        "прыродаахоўная дзейнасць (па напрамках)," +
        "энергаэфектыўныя тэхналогіі і энергетычны менеджмент," +
        "ядзерная і радыяцыйная бяспека",

        "камп'ютэрная матэматыка і сістэмны аналіз," +
        "матэматыка (напрамак - навукова-канструктарская дзейнасць)," +
        "матэматыка (напрамак - навукова-педагагічная дзейнасць)," +
        "матэматыка (напрамак - навукова-вытворчая дзейнасць)," +
        "матэматыка (напрамак - эканамічная дзейнасць)," +
        "матэматыка і інфармацыйныя тэхналогіі (напрамак - вэб-праграмаванне і інтэрнэт-тэхналогіі)," +
        "матэматыка і інфармацыйныя тэхналогіі (напрамак - матэматычнае і праграмнае забеспячэнне мабільных прылад)," +
        "механіка і матэматычнае мадэляванне," +
        "механіка і матэматычнае мадэляванне - сумесная праграма БДУ і ДПУ",

        "геаграфія (па напрамках)," +
        "геаінфармацыйныя сістэмы (напрамак - зямельна-кадастравыя)," +
        "геалогія і разведка радовішчаў карысных выкапняў," +
        "геаэкалогія," +
        "гідраметэаралогія," +
        "космааэракартаграфія",

        "інфармацыя і камунікацыя",

        "лінгвакраіназнаўства," +
        "міжнароднае права," +
        "міжнародныя адносіны," +
        "менеджмент (напрамак - менеджмент у сферы міжнароднага турызму)," +
        "сусветная эканоміка," +
        "мытная справа",

        "актуарная матэматыка," +
        "інфарматыка," +
        "камп'ютэрная бяспека (напрамак - матэматычныя метады і праграмныя сістэмы)," +
        "прыкладная інфарматыка (напрамак - праграмнае забеспячэнне камп'ютэрных сістэм)," +
        "прыкладная матэматыка (напрамак - навукова-вытворчая дзейнасць)," +
        "эканамічная кібернетыка (напрамак - матэматычныя метады і камп'ютэрнае мадэляванне ў эканоміцы)",

        "аэракасмічныя радыёэлектронныя і інфармацыйныя сістэмы і тэхналогіі," +
        "камп'ютэрная бяспека (напрамак - радыёфізічныя метады і праграмна-тэхнічныя сродкі)," +
        "прыкладная інфарматыка (напрамак - інфармацыйныя тэхналогіі тэлекамунікацыйных сістэм)," +
        "радыёфізіка," +
        "фізічная электроніка",

        "псіхалогія," +
        "сацыяльная работа (па напрамках)," +
        "сацыяльныя камунікацыі," +
        "сацыялогія," +
        "філасофія",

        "камп'ютэрная фізіка," +
        "фізіка (напрамак - навукова-даследчая дзейнасць)," +
        "фізіка (напрамак - вытворчая дзейнасць)," +
        "фізіка (напрамак - вытворчая дзейнасць) - сумесная праграма БДУ і ДПУ," +
        "фізіка нанаматэрыялаў і нанатэхналогій," +
        "ядзерныя фізіка і тэхналогіі",

        "рамана-германская (англійская) філалогія," +
        "усходняя (кітайская) філалогія," +
        "рамана-германская (італьянская) філалогія," +
        "рамана-германская (нямецкая) філалогія," +
        "рамана-германская (французская) філалогія",

        "фундаментальная хімія," +
        "хімія (напрамак - навукова-педагагічная дзейнасць)," +
        "хімія (напрамак - навукова-вытворчая дзейнасць)," +
        "хімія (напрамак - фармацэўтычная дзейнасць)," +
        "хімія высокіх энергій," +
        "хімія лекавых злучэнняў",

        "менеджмент (напрамк - міжнародны менеджмент)," +
        "фінансы і крэдыт," +
        "эканоміка," +
        "эканамічная інфарматыка," +
        "эканамічная тэорыя",

        "паліталогія (напрамак - палітыка-юрыдычная дзейнасць)," +
        "правазнаўства," +
        "эканамічнае права"
    ];

function getDepartmentValuesBySpeciality(index){
    var specialityValues;
    if (userLang === "ru_RU") {
        specialityValues = specialityValuesRussian[index];
    } else if (userLang === "en_US") {
        specialityValues = specialityValuesEnglish[index];
    } else if (userLang === "be_BY") {
        specialityValues = specialityValuesBelarussian[index];
    } else {
        specialityValues = specialityValuesEnglish[index];
    }
    return specialityValues.split(",");
}

function changeDepartmentValues(index) {
    let currentSpecialityValues = getDepartmentValuesBySpeciality(index);
    let currentSpecialityValuesCount = currentSpecialityValues.length;
    let specialityList =
        document.forms["editForm"].elements["speciality"];
    specialityList.length = 0;
    for (let i = 0; i < currentSpecialityValuesCount; i++) {
        if (document.createElement) {
            let newSpecialityListOption =
                document.createElement("OPTION");
            newSpecialityListOption.text = currentSpecialityValues[i];
            newSpecialityListOption.value = currentSpecialityValues[i];
            (specialityList.options.add) ?
                specialityList.options.add(newSpecialityListOption) :
                specialityList.add(newSpecialityListOption, null);
        } else {
            specialityList.options[i] =
                new Option(currentSpecialityValues[i], currentSpecialityValues[i], false, false);
        }
    }
}
changeDepartmentValues(
    document.forms["editForm"].elements["department"].selectedIndex);

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

function deleteRequiredAttributeByIdElement() {
    document.getElementById("first-name").required = false;
    document.getElementById("last-name").required = false;
    document.getElementById("middle-name").required = false;
    document.getElementById("day").required = false;
    document.getElementById("month").required = false;
    document.getElementById("year").required = false;
}

function setChoosingInformation() {
    let checkbox1 = document.getElementById('checkbox-1');
    let checkbox2 = document.getElementById('checkbox-2');
    let changeCase = document.getElementById('changeCase');
    if (checkbox1.checked && checkbox2.checked) {
        changeCase.value = "2";
    } else if (checkbox2.checked) {
        changeCase.value = "1";
        deleteRequiredAttributeByIdElement();
    } else if (checkbox1.checked) {
        changeCase.value = "0";
    } else {
        changeCase.value = "";
    }
}

let attempt = document.getElementById("attemptNull");
if (attempt.value === "0") {
    document.getElementById("checkbox-2").disabled = true;
    document.getElementById("label-checkbox-2").style.background = "grey";
}