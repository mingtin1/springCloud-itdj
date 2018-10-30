// layui方法
layui.use(['table', 'form', 'layer', 'vip_table'],
    function () {

        // 操作对象
        var form = layui.form
            , table = layui.table
            , layer = layui.layer
            , vipTable = layui.vip_table
            , $ = layui.jquery;

        // 表格渲染
        var tableIns = table.render({
            elem: '#dateTable'                  //指定原始表格元素选择器（推荐id选择器）
            , height: vipTable.getFullHeight()    //容器高度
            , cols: [[                  //标题栏
                {checkbox: true, sort: true, fixed: true, space: true}
                , {
                    field: 'userId',
                    title: 'ID',
                    align: 'center',
                    width: 80,
                    sort: true,
                    fixed: 'left',
                }
                , {field: 'username', title: '用户名', align: 'center', width: 80}
                , {field: 'phone', title: '手机号', align: 'center', width: 150}
                , {field: 'deptName', title: '所属部门', align: 'center', width: 120}
                , {field: 'createTime', title: '创建时间', align: 'center', width: 180, sort: true}
                , {field: 'delFlag', title: '状态', align: 'center', width: 100, sort: true, templet: '#delFlag'}
                , {fixed: 'right', title: '操作', width: 180, align: 'center', toolbar: '#barOption'} //这里的toolbar值是模板元素的选择器
            ]]
            , id: 'dataCheck'
            , url: '/user/userPage/' //数据接口
            , page: true
            , limits: [10, 20, 50]
            , done: function (res, curr, count) {
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                console.log(res);
                //得到当前页码
                console.log(curr);
                //得到数据总量
                console.log(count);
            }
        });

        // 获取选中行
        table.on('checkbox(dataCheck)', function (obj) {
            layer.msg('123');
            console.log(obj.checked); //当前是否选中状态
            console.log(obj.data); //选中行的相关数据
            console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
        });

        // 刷新
        $('#btn-refresh').on('click', function () {
            tableIns.reload();
        });
    });

function onAddBtn(){
    //页面层-自定义
    layer.open({
        type: 1,
        title:"新建配置",
        closeBtn: false,
        shift: 2,
        area: ['400px', '300px'],
        shadeClose: true,
        // btn: ['新增', '取消'],
        // btnAlign: 'c',
        content: $("#btn-add"),
        success: function(layero, index){},
        yes:function(){

        }
    });
}