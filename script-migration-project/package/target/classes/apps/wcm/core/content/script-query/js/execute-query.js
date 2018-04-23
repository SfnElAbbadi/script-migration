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
			var add = $('.granite-execute-query .granite-add-section').parent();
            var remove = $('.granite-execute-query .granite-remove-section').parent();

            if(checkedbox.val() == 'add-modify'){
                add.show();
                remove.hide();
            }else if(checkedbox.val() == 'remove'){
				add.hide();
                remove.show();
            }


        });
    },400);

})(document, Granite.$);