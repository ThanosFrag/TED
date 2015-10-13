
    function check(input) {
        if (input.value != document.getElementById('password').value) {
            input.setCustomValidity('Password Must be Matching.');
        } else {
            // input is valid -- reset the error message
            input.setCustomValidity('');
        }
    }

    function checkUser(input) {
        if (flag==1) {
            input.setCustomValidity('UserName Already Taken.');
        } else {
            // input is valid -- reset the error message
            input.setCustomValidity('');
        }
    }
    function confirmComplete(price) {
      	
      	var answer=confirm("Are you sure you want to continue");
      	if (answer==true)
      	  {
  
      	    return true;
      	  }
      	else
      	  {
      	    return false;
      	  }
      	}
    function confirmCategory() {
      	
      	var answer=('div.checkbox.required :checkbox:checked').length > 0;
      	if (answer==true)
      	  {
  
      	    return true;
      	  }
      	else
      	  {
      	    return false;
      	  }
      	}   
  
    function displayMapAt(lat, lon) {
        $("#map")
                .html(
                        "<iframe id=\"map_frame\" "
                                + "width=\"100%\" height=\"600px\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\" "
                                + "src=\"https://www.google.gr/maps?f=q&amp;output=embed&amp;source=s_q&amp;hl=sk&amp;geocode=&amp;q=https:%2F%2Fwww.google.sk%2Fmaps%2Fms%3Fauthuser%3D0%26vps%3D5%26hl%3Dsk%26ie%3DUTF8%26oe%3DUTF8%26msa%3D0%26output%3Dkml%26msid%3D205427380680792264646.0004fe643d107ef29299a&amp;aq=&amp;sll=48.669026,19.699024&amp;sspn=4.418559,10.821533&amp;ie=UTF8&amp;ll="
                                + lat + "," + lon
                                + "&amp;spn=0.199154,0.399727&amp;t=m&amp;z="
                                + 10 + "\"" + "></iframe>");

    }
 