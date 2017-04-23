var Main = (function (out, $){
    "use strict";
    
    var $childrenBirthDaysPrototype = $('#childrenBirthDays-prototype');
    var $childrenBirthDaysSection = $('#childrenBirthDays-section');
    
    var triggers = {
        '#married': {
            'change': function (){
                var married = $(this).val() === 'true';
                $('.ifMarried').toggleClass('hidden', !married);
            }
        },
        '#children': {
            'change': function (){
                var expectedNumber = $(this).val() || 0;
                var actualNumber = $childrenBirthDaysSection
                    .find('.date-picker')
                    .length;
                $childrenBirthDaysSection.toggleClass('hidden', expectedNumber === 0);
                
                if (expectedNumber === actualNumber){
                    return;
                }
                
                while (actualNumber > expectedNumber){
                    $childrenBirthDaysSection
                        .find('.date-picker')
                        .last()
                        .remove();
                    
                    actualNumber = $childrenBirthDaysSection
                        .find('.date-picker')
                        .length;
                }
                
                while (expectedNumber > actualNumber){
                    $childrenBirthDaysPrototype.clone(true)
                        .removeClass('hidden')
                        .addClass('date-picker')
                        .appendTo('#childrenBirthDays-section');
                    
                    actualNumber = $childrenBirthDaysSection
                        .find('.date-picker')
                        .length;
                }
                setUpChildrenDatePickers();
                enableDatePicker($childrenBirthDaysSection.find('.date-picker'));
            }
        }
    };
    
    function enableDatePicker($input){
        $input.datepicker({
            dateFormat: "dd.mm.yy",
            showButtonPanel: true
        });
    }
    
    function onReady(){
        var questionUrl = $("#questionUrl").val();
        $("label[data-question]").each(function (){
            var $that = $(this);
            $.get(questionUrl + '?key=' + $that.data('question'), function (question){
                $that.text(question);
                // $that.parent().find('input').setAttribute('placeholder', question);
            })
        });
        
        enableDatePicker($('.date-picker'));
        
        $('#married').change();
        $('#children').change();
    }
    
    function setUpChildrenDatePickers(){
        $('#childrenBirthDays-section')
            .find('.date-picker')
            .each(function (index, selector){
                var path = 'childrenBirthDays[' + index + ']';
                $(selector)
                    .attr('id', path)
                    .attr('name', path);
            });
    }
    
    out.triggers = triggers;
    out.onReady = onReady;
    
    return out;
})(Main || {}, jQuery);

$(document).ready(function (){
    for (var $selector in Main.triggers){
        for (var event in Main.triggers[$selector]){
            $($selector).on(event, Main.triggers[$selector][event]);
        }
    }
    Main.onReady();
});
