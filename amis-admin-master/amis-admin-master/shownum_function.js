function updateTextWithNumber() {
  // 在10秒后执行以下代码
  setTimeout(function() {
    // 获取要更新的文本元素
    var textWithNumberElement = document.getElementById("text-with-number");
    // 将文本中的数字从0变成1
    textWithNumberElement.innerHTML = "测试用例：1"+"\n"+"碰撞数量：1"+"\n"+"融合错误：0";
  }, 10000); // 10000毫秒 = 10秒

}
window.onload = updateTextWithNumber;
