/**
 * Created by Administrator on 2018/6/1 0001.
 */
function getDepositFloat(deposit) {
    var money=Math.round(parseFloat(deposit)*100)/100;
    var arr=money.toString().split('.');
    if(arr.length==1)
        return money.toString()+".00";
    if(arr.length>1)
        if(arr[1].length==1)
            return money.toString()+"0";
    return money;
}
