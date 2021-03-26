<script language="javascript">
    $(function () {
        dropdown1 = new uldropdown({
            dropid: 'dropdown1', // id of menu DIV container
            overlay: false, // true = drop down, false = expanding menu
            onSelect($selected){ // when user selects a value
                $('#output').val(
                    'Selected Text: ' + $selected.text() + '\n\n' + 'Selected Value: ' + $selected.attr('href'));
            }
        })
    })
</script>

<div class="frame_menu">
    <div class="uldropdown"><div><a href="javascript:gotoFunction('/dashboard');"  target="_parent" style="color:white"><s:message code="menu.index.root" text='首頁' /></a></div></div>
    <div id="dropdown1" class="uldropdown">
        <div><s:message code="invoice.root.menu.name" text='電子發票' /></div>
        <ul>
            <li><a href="${contextPath}/invoice" target="_parent"><s:message code="invoice.detail.page.title.name" text='電子發票上傳結果' /></a></li>
        </ul>
    </div>

</div>
