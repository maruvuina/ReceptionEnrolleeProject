var userLang = document.getElementsByTagName("html")[0].getAttribute("lang");

function getFileButtonText(){
    var file_button_text;
    if (userLang === "ru_RU") {
        file_button_text = 'Выберите фотографию';
    } else if (userLang === "en_US") {
        file_button_text = 'Choose photo';
    } else if (userLang === "be_BY") {
        file_button_text = 'Выберыце фотаздымак';
    } else {
        file_button_text = 'Choose photo';
    }
    return file_button_text;
}

function fileInput(fi_container_class, fi_button_class, fi_filename_class, fi_button_text) {
	fi_container_class = fi_container_class || 'fileUpload';
	fi_button_class	= fi_button_class || 'fileBtn';
	fi_filename_class = fi_filename_class || 'fileName';
	fi_button_text = fi_button_text ||getFileButtonText();
	var fi_file = $('input[type = file]');
	fi_file.css('display', 'none');
	var fi_str = '<div class="' + fi_container_class + '"><div class="' + fi_button_class + '">' + fi_button_text + '</div><div class="' + fi_filename_class + '"></div></div>';
	fi_file.after(fi_str);
	var fi_count = fi_file.length;
	for (var i = 1; i <= fi_count; i++) {
		var fi_file_name = fi_file.eq(i - 1).attr('name');
		$('.' + fi_container_class).eq(i - 1).attr('data-name', fi_file_name);
	}
	$('.' + fi_button_class).on('click', function() {
		var fi_active_input = $(this).parent().data('name');
		$('input[name=' + fi_active_input + ']').trigger('click');
	});
	fi_file.on('change', function() {
		var fi_file_name = $(this).val();
		var fi_real_name = $(this).attr('name');
		var fi_array = fi_file_name.split('\\');
		var fi_last_row = fi_array.length - 1;
			fi_file_name = fi_array[fi_last_row]; //
		$('.' + fi_container_class).each(function() {
			var fi_fake_name = $(this).data('name');
			if(fi_real_name === fi_fake_name) {
				$('.' + fi_container_class + '[data-name=' + fi_real_name + '] .' + fi_filename_class).html(fi_file_name);
			}
		});
	});
}