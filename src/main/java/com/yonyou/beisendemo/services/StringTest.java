package com.yonyou.beisendemo.services;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.yonyou.beisendemo.entity.FileTest2;
import com.yonyou.beisendemo.entity.TestEntity;
import com.yonyou.beisendemo.utils.DateUtil;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StringTest {

    public static void main(String[] args) {

//        BigDecimal bigDecimal = new BigDecimal("50000");
//        System.out.println(bigDecimal);


//
//        TestEntity testEntity = new TestEntity();
//        testEntity.setName("test");
//        testEntity.setId("111");
//        String ssss = ("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.review.km.kmss.landray.com/\"><soapenv:Header><tns:RequestSOAPHeader xmlns:tns=\"http://sys.webservice.client\"><tns:user>foreigntest</tns:user><tns:password>b911b6302e0b1ba3b281f5db132c012f</tns:password></tns:RequestSOAPHeader></soapenv:Header><soapenv:Body><web:approveProcess><arg0><fdId>191e4c9a7e15f0d8fb7cc2f48c6aa8f2</fdId><flowParam>{\"operationType\":\"handler_pass\"}</flowParam><docContent></docContent><docCreator>{\"LoginName\":\"linchen\"}</docCreator><docSubject>因公出访申请审批</docSubject><fdTemplateId>18df291c123f36c5ed4ecda4c9c83f42</fdTemplateId><formValues>{\"fd_task_detail.fd_country1_text\":[\"阿富汗\"],\"fd_task_detail.fd_country_about\":[\"奇尔韦尔\\n企微\"],\"fd_confirm\":\"01\",\"fd_task_detail.fd_inviter_business\":[\"阿萨德\"],\"fd_ISpublicity\":\"否\",\"fd_inviter_detail.fd_country3_text\":[\"阿富汗\"],\"fd_inviter_detail.fd_inviter_address\":[\"123\"],\"fd_inviter_detail.fd_country3\":[\"阿富汗\"],\"fd_fee_detail.fd_international_travel_fee\":[\"1.00000000\"],\"fd_ISintermediary\":\"是\",\"fd_leadtype2\":\"下属企业领导班子副职\",\"fd_visitor_detail.fd_type\":[\"团长\"],\"fd_visitor_detail.fd_dept\":[\"院领导;党委工作部\"],\"fd_fee_detail.fd_incidental_expenses\":[\"1.00000000\"],\"fd_fee_detail.fd_other_expenses\":[\"1.00000000\"],\"fd_visitor_detail.fd_person_id\":[\"191084901fbcd3b25dd56b246f2bcf5e\"],\"fd_fee_detail.fd_accommodation\":[\"1.00000000\"],\"fd_task_detail.fd_inviter_about\":[\"阿萨德\"],\"fd_groupcomp1\":\"广汽研究院\",\"fd_target\":\"12\\n3456\\n123\",\"fd_visitor_detail.fd_company\":[\"广汽研究院\"],\"fd_task_detail.fd_days1\":[\"2\"],\"fd_inviter_detail.fd_inviter_post\":[\"213\"],\"fd_visitor_detail.fd_duties\":[\"院长\"],\"fd_task_detail.fd_country1\":[\"阿富汗\"],\"fd_task_detail.fd_purpose\":[\"萨达\\n阿萨德\\n阿萨德\\n\"],\"fd_fee_detail.fd_name2\":[\"吴坚\"],\"fd_startdate\":\"2024-09-12\",\"fd_schedule_id\":\"191e4c9974b6d3bc9cc34f74a1d9e4c5\",\"fd_road_country_detail.fd_transit_type\":[\"一次过境\"],\"fd_task\":\"12\\n3456\\n123\",\"fd_fee_detail.fd_board_expenses\":[\"1.00000000\"],\"fd_fee_source_type\":\"派员单位自理\",\"fd_inviter_detail.fd_company_name\":[\"12e\"],\"fd_finishdate\":\"2024-09-13\",\"fd_battribute\":\"04\",\"fd_ISeffective\":\"否\",\"fd_company_id\":\"2086569089287323653\",\"fd_visitor_detail.fd_sex\":[\"M\"],\"fd_inviter_detail.fd_inviter_phone\":[\"213\"],\"fd_road_country_detail.fd_stop_days\":[\"2\"],\"fd_visitor_detail.fd_main_task\":[\"11\\n12\\n\\n\\n\"],\"fd_visitor_detail.fd_responsibilities\":[\"11\"],\"fd_inviter_detail.fd_inviters\":[\"123\"],\"fd_task_detail.fd_activity\":[\"爱思\"],\"fd_leadtype2_text\":\"下属企业领导班子副职\",\"fd_road_country_detail.fd_road_country_text\":[\"阿富汗\"],\"fd_visitor_detail.fd_age\":[\"57\"],\"fd_iISspecail\":\"否\",\"fd_ISplan\":\"否\",\"fd_company_type\":\"04\",\"fd_task_detail.fd_subject\":[\"阿萨德\"],\"fd_road_country_detail.fd_road_country\":[\"阿富汗\"],\"fd_groupcomp1_text\":\"广州汽车集团股份有限公司汽车工程研究院\",\"fd_other_source\":\"\",\"fd_other_illustrate\":\"\",\"fd_ISretire\":\"否\",\"fd_visitor_detail.fd_name1\":[\"吴坚\"],\"fd_foreman_name\":\"吴坚\"}</formValues></arg0></web:approveProcess></soapenv:Body></soapenv:Envelope>");
////        String s = testEntity.getMessage().replaceAll("\\n" , "");
//        String ss = ssss.replaceAll("\\n" , "");
////        ObjectMapper objectMapper = new ObjectMapper();
////        ObjectWriter writer = objectMapper.writer(SerializationFeature.valueOf(JSONUtil.toJsonStr(testEntity.getMessage())));
//        Map map = new HashMap();
////        try {
////             map = objectMapper.readValue(s, new TypeReference<Map>() {
////            });
////        }catch (Exception ex){
////            ex.printStackTrace();
////        }
//
//        System.out.println(ss);

//        List<FileTest2> test = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            FileTest2 fileTest2 = new FileTest2();
//            fileTest2.setFile3(StrUtil.toString(i));
//            fileTest2.setFile5(i);
//            test.add(fileTest2);
//            i++;
//        }
//
//        test = test.stream().filter(e-> StrUtil.isEmpty(e.getFile4()) ).collect(Collectors.toList());
//        for (int i = 0; i < test.size(); i++) {
//            System.out.println(JSONUtil.toJsonStr(test.get(i)));
//        }
//
//        Date payTime = cn.hutool.core.date.DateUtil.offsetDay( cn.hutool.core.date.DateUtil.parse("2025-03-02 11:20:20") , 365);
//        boolean bl = DateUtil.isAfterOrEqualToToday(DateUtil.getDateFormat(payTime , "yyyy-MM-dd") , "yyyy-MM-dd");
//        System.out.println(bl);
//        List<Map> rows = new ArrayList<>();
//        Map map = MapUtil.of("drivingLicenseUrl" , "123321123312");
//        rows.add(map);
//        String drivingLicenseUrl1 = CollUtil.isEmpty(rows) ? "111"
//                : rows.stream()
//                .findFirst()
//                .map(e -> StrUtil.toString(e.get("drivingLicenseUrl")))
//                .map(value -> value + "?invokeType=GRT_INNER_INVOKE")
//                .orElse("");
//        System.out.println(drivingLicenseUrl1);

//        String s = "null";
//        if (StrUtil.isNotBlank(s)){
//            System.out.println("111");
//        }else {
//            System.out.println("222");
//        }
//        ListNode l1 = new ListNode(1);
//        ListNode l2 = new ListNode(2);
//        l2.next = l1;
//        ListNode l3 = new ListNode(3);
//        l3.next = l2;
//        ListNode l4 = new ListNode(4);
//        ListNode l5 = new ListNode(5);
//        l5.next = l4;
//        ListNode l6 = new ListNode(6);
//        l6.next = l5;
//        System.out.println(JSONUtil.toJsonStr(addTwoNumbers(l3 , l6)));

//        String [] str =new String[]{"eat","tea","tan","ate","nat","bat"};
//        List<List<String>> lists = groupAnagrams(str);
//        System.out.println(JSONUtil.toJsonStr(lists));

//        System.out.println(sign("YONYOU_GRTXyt4onS9pNFfgOqAAjeYNL" , 1748938540000L , "2Ndealrough^0nature&%*954usickR(qn&3vprabbit5C^brought)OK!quiteCpain^5strongdFl0productD1osQdC"));
//        LocalDate today = LocalDate.now();
//        LocalDate yesterday = today.minusDays(1);
//        String yesterdayStr = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        System.out.println(yesterdayStr);
//        List<Map> par = new ArrayList<>();
//        Map map = new HashMap();
//        map.put("name" , "张三");
//        map.put("age" , 18);
//        par.add(map);
//        Map map1 = new HashMap();
//        map1.put("total" , 11);
//        map1.put("data" , par);
//        Map map2 = new HashMap();
//        map2.put("result" , "1");
//        map2.put("data" , map1);
//        JSONObject js = com.alibaba.fastjson2.JSONObject.parseObject(JSONUtil.toJsonStr(map2));
//        System.out.println(JSONUtil.toJsonStr(js));
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        System.out.println( yesterday);
    }

    public static String sign(String appKey, long timestamp, String secretKey) {
        String toBeHashed = appKey + "_" + timestamp + "_" + secretKey;
        return DigestUtils.md5Hex(toBeHashed.toUpperCase()).toUpperCase();
    }


    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(0);
        ListNode cur = pre;
        int ca = 0;
        while(l1!=null || l2!=null){
            int x = l1==null?0:l1.getVal();
            int y = l2==null?0:l2.getVal();
            int sum = x+y+ca;
            ca = sum / 10;
            sum = sum % 10;
            cur.next = new ListNode(sum);
            cur = cur.next;
            if(l1 != null) l1 = l1.next; if(l2 != null) l2 = l2.next;
        }
        if(ca == 1){
            cur.next = new ListNode(ca);
        }
        return pre.next;
    }

        public static List<List<String>> groupAnagrams(String[] strs) {
            return new ArrayList<>(Arrays.stream(strs).collect(Collectors.groupingBy(s->{
                char[] chars = s.toCharArray();
                Arrays.sort(chars);
                return new String(chars);
            })).values());

        }


    public static class ListNode {
        private int val;
        private ListNode next;

        public ListNode(int val) {
            this.val = val;
            this.next = null;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }
    }

}

