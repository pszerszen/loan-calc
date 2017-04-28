var Main = (function (out, $){
    "use strict";
    
    var $childrenBirthDaysPrototype = $('#childrenBirthDays-prototype');
    var $childrenBirthDaysSection = $('#childrenBirthDays-section');
    var $form = $('form');
    var $verdict = $('#verdict');
    
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
        },
        '#submit': {
            'click': function (){
                $('#cleanVerdict').click();
                $.ajax({
                    url: $form.attr('action'),
                    dataType: 'json',
                    type: 'POST',
                    data: $form.serialize(),
                    traditional: true,
                    success: displayVerdict,
                    error: function (xhr){
                        alert(xhr.responseJSON.message)
                    }
                });
            }
        },
        '#cleanVerdict': {
            'click': function (){
                $verdict.find('#points').find('li').find('span').text('');
                $verdict.find('#rules').find('li').not('.active').remove();
                $verdict.addClass('hidden');
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
    
    function displayVerdict(verdict){
        var $points = $verdict.find('#points');
        $points.find('.list-group-item-success').find('span').text(verdict.positives);
        $points.find('.list-group-item-warning').find('span').text(verdict.warnings);
        $points.find('.list-group-item-danger').find('span').text(verdict.critical);
        
        var $rulesLog = $verdict.find('#rules');
        $rulesLog.find('li').not('.active').remove();
        for (var i = 0; i < verdict.rulesLog.length; i++){
            var $li = $('<li></li>')
                .addClass('list-group-item')
                .text(verdict.rulesLog[i]);
            $rulesLog.append($li);
        }
        
        $verdict.removeClass('hidden');
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
