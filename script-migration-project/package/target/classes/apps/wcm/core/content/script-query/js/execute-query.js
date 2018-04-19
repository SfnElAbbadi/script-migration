/*************************************************************************
 *
 * ADOBE CONFIDENTIAL
 * __________________
 *
 *  Copyright 2015 Adobe Systems Incorporated
 *  All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 **************************************************************************/
(function(document, $) {
    "use strict";

    setTimeout(function(){

        var checkebox = $('input[name=operation]');

        $(document).on("change", checkebox, function(e) {

            var checkedbox = $('input[name=operation]:checked');
			var add = $('.granite-execute-query .coral-Form-fieldset[data-name="granite-add-section"]');
            var remove = $('.granite-execute-query .coral-Form-fieldset[data-name="granite-remove-section"]');

            if(checkedbox.val() == 'add-modify'){
                add.show();
                remove.hide();
            }else if(checkedbox.val() == 'remove'){
				add.hide();
                remove.show();
            }


        });

		var counter = 0;

        var name  = $('.granite-execute-query input[name=property-name]'),
            type  = $('.granite-execute-query input[name=property-type]'),
            value = $('.granite-execute-query input[name=property-value]');

        $(document).on('click','#granite-popup-add' ,function(){
        
            if(name.val() !== ''  && type.val() !== '' && value.val() !== ''){

                var html = '';
                html += '<section class="added-property">';
                    html += '<span class="add-element">'+ name.val() +'</span>';
                    html += '<span class="add-element">'+ $('.granite-execute-query .select-type span').text() +'</span>';
                    html += '<span class="add-element">'+ value.val() +'</span>';
                    html += '<input type="hidden" name="name'+counter+'" value="'+ name.val() +'" />';
                    html += '<input type="hidden" name="type'+counter+'" value="'+ type.val() +'" />';
                    html += '<input type="hidden" name="value'+counter+'" value="'+ value.val() +'" />';
                    html += '<span class="remove-property">x</span>';
                html += '</section>';
        
                $('section[data-name="granite-add-section"]').append(html);
        
                name.val('');
                value.val('');
        
                counter++;
                
                $('#add-counter').val(counter);
                
            }else{
                return false;
            }
        
        });
        
        $(document).on('click', '.remove-property', function(){

            $(this).parent().remove();
            counter--;
            $('#add-counter').val(counter);
        });


		var counterRem = 0;
		var nameRem  = $('.granite-execute-query input[name=remove-property-name]');

        $(document).on('click','#granite-popup-rem' ,function(){


            if(nameRem.val() !== ''){

                var html2 = '';
                html2 += '<section class="added-property-rem">';
                    html2 += '<span class="add-element-rem">'+ nameRem.val() +'</span>';
                    html2 += '<input type="hidden" name="name'+counterRem+'" value="'+ nameRem.val() +'" />';
                    html2 += '<span class="rem-property">x</span>';
                html2 += '</section>';

                $('section[data-name="granite-remove-section"]').append(html2);

                nameRem.val('');

                counterRem++;

                $('#rem-counter').val(counterRem);

            }else{
                return false;
            }
        
        });
        
        $(document).on('click', '.rem-property', function(){
            
            $(this).parent().remove();
            counterRem--;
            $('#rem-counter').val(counterRem);
        });



    },400);


//   $(document).on("submit", ".granite-execute-query", function(e) {
//   		e.preventDefault();
//        explainQuery();
//         var dialog = document.querySelector("#popup");
//        dialog.show();    
//    });

    
//    $(document).on("submit", "#granite-popup-form", function(e) {
//        e.preventDefault();
//        var dialog = document.querySelector("#popup");
//        
//        $.ajax({
//            url: Granite.HTTP.externalize("/apps/wcm/core/content/script-query/add-form-validation/POST.jsp"),
//            type: 'GET',
//            data: $( this ).serialize(),
//            timeout: 60000,
//            dataType: 'json',
//            success: function (data) {
//                dialog.hide();
//                
//            }
//        });
//
//    });
        
//    function explainQuery() {
//        var searchPath = document.querySelector("*[name=searchPath]").value,
//            query = document.querySelector("*[name=query]").value;
//
//        $.ajax({
//            url: Granite.HTTP.externalize("/apps/cq/gui/components/siteadmin/admin/sqlqueryscript/page/POST.jsp"),
//            type: 'GET',
//            async: true,
//            data: {
//                'searchPath' : searchPath,
//                'query' : query
//            },
//            timeout: 60000,
//            headers: {
//                'Content-Type': 'application/x-www-form-urlencoded'
//            },
//            dataType: 'json',
//            success: function (data) {
//                console.log(data)      
//            },
//
//        });

    //}

})(document, Granite.$);