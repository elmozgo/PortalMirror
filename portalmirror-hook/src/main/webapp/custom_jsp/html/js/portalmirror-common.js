function debounce(func, threshold, execAsap) {

	var timeout;

	return function debounced() {
		var obj = this, args = arguments;
		function delayed(){
			if (!execAsap)
				func.apply(obj, args);
			timeout = null;
		};

		if(timeout){

			clearTimeout(timeout);
		} else if(execAsap){

			func.apply(obj, args);
		}

		timeout = setTimeout(delayed, threshold || 100);
	};

}

jQuery.fn.updateWithText = function(text, speed)
{
	var dummy = $('<div/>').html(text);

	if ($(this).html() != dummy.html())
	{
		$(this).fadeOut(speed/2, function() {
			$(this).html(text);
			$(this).fadeIn(speed/2, function() {
				//done
			});
		});
	}
}

jQuery.fn.outerHTML = function(s) {
    return s
        ? this.before(s).remove()
        : jQuery("<p>").append(this.eq(0).clone()).html();
};

function roundVal(temp)
{
	return Math.round(temp * 10) / 10;
}
