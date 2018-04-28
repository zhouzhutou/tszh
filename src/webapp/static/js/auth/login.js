function submitForm()
{
    $('#ff').form('submit',{
        onSubmit:function(){
            return $(this).form('enableValidation').form('validate');
        }
    });
}
