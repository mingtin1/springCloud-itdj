
// layui方法
layui.use(['tree', 'table', 'vip_table', 'layer'], function () {

    // 操作对象
    var table = layui.table
        , vipTable = layui.vip_table
        , layer = layui.layer
        , $ = layui.jquery;

    // 表格渲染
//        var tableIns = table.render({
//            elem: '#dateTable'                  //指定原始表格元素选择器（推荐id选择器）
//            , height: vipTable.getFullHeight()    //容器高度
//            , cols: [[                  //标题栏
//                {checkbox: true, sort: true, fixed: true, space: true}
//                , {field: 'id', title: 'ID', width: 80}
//                , {field: 'account', title: '用户名', width: 120}
//                , {field: 'auth_group_name', title: '权限组', width: 120}
//                , {field: 'last_login_time', title: '最后登录时间', width: 180}
//                , {field: 'last_login_ip', title: '最后登录IP', width: 180}
//                , {field: 'create_time', title: '创建时间', width: 180}
//                , {field: 'status', title: '状态', width: 70}
//                , {fixed: 'right', title: '操作', width: 150, align: 'center', toolbar: '#barOption'} //这里的toolbar值是模板元素的选择器
//            ]]
//            , id: 'dataCheck'
//            , url: './../json/data_table.json'
//            , method: 'get'
//            , page: true
//            , limits: [30, 60, 90, 150, 300]
//            , limit: 30 //默认采用30
//            , loading: false
//            , done: function (res, curr, count) {
//                //如果是异步请求数据方式，res即为你接口返回的信息。
//                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
//                console.log(res);
//
//                //得到当前页码
//                console.log(curr);
//
//                //得到数据总量
//                console.log(count);
//            }
//        });

    // 获取选中行
    table.on('checkbox(dataCheck)', function (obj) {
        console.log(obj.checked); //当前是否选中状态
        console.log(obj.data); //选中行的相关数据
        console.log(obj.type); //如果触发的是全选，则为：all，如果触发的是单选，则为：one
    });

    // 树        更多操作请查看 http://www.layui.com/demo/tree.html
    layui.tree({
        elem: '#tree' //传入元素选择器
        , click: function (item) { //点击节点回调
            layer.msg('当前节名称：' + item.name);
            // 加载中...
            var loadIndex = layer.load(2, {shade: false});
            // 关闭加载
            layer.close(loadIndex);
            // 刷新表格
            tableIns.reload();
        }
        , nodes: [{ //节点
            name: '父节点1'
            , children: [{
                name: '子节点11'
                , children: [{
                    name: '子节点111'
                }]
            }, {
                name: '子节点12'
            }]
        }, {
            name: '父节点2'
            , children: [{
                name: '子节点21'
                , children: [{
                    name: '子节点211纷纷就爱我就覅偶而安静佛尔'
                }]
            }]
        }]
    });

    // you code ...


});