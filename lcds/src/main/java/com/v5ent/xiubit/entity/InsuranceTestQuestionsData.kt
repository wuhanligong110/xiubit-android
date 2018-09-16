package com.v5ent.xiubit.entity

/**
 * Created by hasee-pc on 2017/12/19.
 */
class InsuranceTestQuestionsData {

    val userFamilyMember : ArrayList<String> = ArrayList<String>()

    val questions = arrayListOf<Question>(
            QuestionSex()
            , QuestionBirthday()
            , QuestionFamilyMember()
            , QuestionFamilyInCome()
            , QuestionFamilyLoan()
            , QuestionFamilySafeGuard())

    companion object {
        /**
         * @param isSingleAnswer 是否单选 false 多选
         */
        open class Question(val firstQus: String,val answers: ArrayList<String>, private val responds: ArrayList<String>, val isSingleAnswer: Boolean = true) {
            /**
             * 默认 回答和机器回应一一对应，多选时则由子类重写视具体情况判定
             */
            open fun getRespondsStr(answersIndexs: HashSet<Int>): String {
                val respondsIndex = getRespondsIndex(answersIndexs)
                if (responds.size >= respondsIndex + 1 ) return responds[respondsIndex]
                else return ""
            }

           open fun getRespondsIndex(answersIndexs: HashSet<Int>): Int = if (answersIndexs.size > 0) {
                answersIndexs.first()
            } else 0

            open fun getAnswerStr(selectedTagsStrArr: ArrayList<String>): String {
                var str= ""
                selectedTagsStrArr.forEachIndexed { index, s ->
                    str += s + ","
                }
                if (str.endsWith(",")) str = str.removeRange(str.length -1,str.length)
                return str
            }

            open fun getForeverChecked(): HashSet<Int>? = null  //固定选中状态的条目index
        }

        val openTaik = arrayListOf<String>("hi！我是小保，是你的家庭保障小助手,偷偷告诉你我可是人工智能机器人哦~~"
        ,"那么。首先让我来了解一下你的家庭吧。"
        )

        class QuestionSex : Question("请先告诉我你的性别吧", arrayListOf("男", "女"), arrayListOf())

        class QuestionBirthday : Question(
                "选择一下你的年龄段吧"
                , arrayListOf("18岁及以下", "18-40岁", "40岁以上")
                , arrayListOf("嘿,少年！人生路上布满荆棘,持剑上路时别忘了早早的为自己添加一份保障哦！"
                , "大好青年哎~~这个年纪的你应该已经肩负不少责任了呢，可以多添置一些保障来分散压力哦！"
                , "人到中年，希望你已学会去接受生活给予的压力。一份健康保障在这个阶段可是不可或缺的哦！")
        )

        class QuestionFamilyMember : Question(
                "你的家庭成员有哪些呢？"
                , arrayListOf("本人", "配偶","父亲","母亲","配偶父亲","配偶母亲", "子女1","子女2")
                , arrayListOf("哇哈哈，看来你还是单身果呢。不过也很好呀，跟小保一样啦。一人吃饱全家不饿，哈哈哈。"
                , "嘿嘿，看来你跟小保一样，也还是个宝宝呀！俗话说“父母在，人生尚有来处”，为他们添置一份保障让亲情永驻"
                , "自己成了家，也忘不了父母的养育之恩，给自己添加保障之余也要为我们年纪渐长的父母一份养老依靠哦！"
                , "哇塞！二人世界诶~好羡慕(✿◡‿◡)。有了彼此的你们，也有了更多的责任，要做好双方的保障哦，毕竟此时彼此便是全世界呢！"
                , "孩子是家庭的未来，小小的他们需要大大的呵护与保障哦！"
                , "有宝宝的你们，真的好幸福呀。对于宝宝，家长才是他们最好的保障，所以就科学的保险规划而言，记住先保大人,后保小孩哦！"
                , "哇塞。还真是一大家子呢，上有老下有小，肩负的责任可不少哦！"
        )
                , false
        ) {
            override
            fun getRespondsIndex(answersIndexs: HashSet<Int>): Int {
                if (answersIndexs.size == 0) return 0
                //单身未选择父母 -- 未选择配偶和任一双亲
                if (isSingle(answersIndexs) && !hasParent(answersIndexs)) return 0
                //单身选择父母
                if (isSingle(answersIndexs) && hasParent(answersIndexs)) return 1
                //有配偶且选择有一方父母未选择小孩
                if (hasMate(answersIndexs) && hasParent(answersIndexs) && !hasChild(answersIndexs)) return 2
//                有配偶未选择一方父母未选择小孩
                if (hasMate(answersIndexs) && !hasParent(answersIndexs) && !hasChild(answersIndexs)) return 3
//                未选择配偶未选择父母有选择小孩
                if (!hasMate(answersIndexs) && !hasParent(answersIndexs) && hasChild(answersIndexs)) return 4
//                选择有配偶未选择父母有选择小孩
                if (hasMate(answersIndexs) && !hasParent(answersIndexs) && hasChild(answersIndexs)) return 5
//                选择配偶选择父母选择小孩
                if (hasMate(answersIndexs) && hasParent(answersIndexs) && hasChild(answersIndexs)) return 6
//                未选择配偶有选择父母有选择小孩
                if (!hasMate(answersIndexs) && hasParent(answersIndexs) && hasChild(answersIndexs)) return 6
                else return 0

            }

           private fun hasMate(answersIndexs: HashSet<Int>) : Boolean = answersIndexs.contains(1)  //有配偶
           private fun hasParent(answersIndexs: HashSet<Int>) : Boolean = answersIndexs.any { it>=2 && it <=5 }  //有父母
           private fun hasChild(answersIndexs: HashSet<Int>) : Boolean = answersIndexs.any { it>=6 && it <=7 }  //有小孩
           private fun isSingle(answersIndexs: HashSet<Int>) : Boolean = !answersIndexs.any { it == 1 || it>=4  }  //单身

            override fun getForeverChecked(): HashSet<Int>? {
                val set = HashSet<Int>()
                set.add(0)
                return set
            }
        }

        class QuestionFamilyInCome : Question(
                "家庭保障的规划,可少不了家庭合计年收入这一项哦！快来选择吧~"
                , arrayListOf("20万以下", "20万-50万", "50万-100万", "100万以上")
                , arrayListOf("点滴的收入都是我们劳动的成果，要努力工作、加油赚钱哦！"
                , "嘿嘿~家庭收入很不错呢！"
                , "这样的家庭收入真的让小保很是羡慕呀，可惜小保木有工资！T^T"
                , "土豪，小保能和你做朋友么 (●ﾟωﾟ●)"
        )
        )

        class QuestionFamilyLoan : Question(
                "家庭是否有贷款呢？家庭合计贷款也是家庭保障规划很重要的因素之一哦~"
                , arrayListOf("无债一身轻", "50万以下", "50~100万", "100~200万", "200万以上")
                , arrayListOf("哈哈，身轻如燕才可一飞冲天呀。恭喜恭喜！"
                , "有压力才会有动力，捋起袖子加油干！努力还贷的同时，一定要做好相关的保障哦！"
                , "给我一个支点,我能翘起地球！利用好金融杠杆，让银行为你打工！但要记得分散风险哦！"
                , "啧啧啧~压力有一丢丢大呀。要加油哦！为保障家庭资产，要针对贷款作相对应的保险规划才是哦！"
                , "肩负的压力不小哦！此时的保障可是相当重要呢！保一份与贷款等额的保险才是明智之选哦！"
        )
        )

        class QuestionFamilySafeGuard : Question(
                "说到底，家庭保障计划最重要的当然还是保障啦。那么，快来告诉我家里的哪些人已经拥有保障了吧！"
                , arrayListOf("都没有")  //动态的，将QuestionFamilyMember用户选择的选项添加到前面
                , arrayListOf("哎呦，不错哦！看来还是很有家庭保障的意识呢，不过仅有一人还是有些欠缺哦，稍后让小保来教你吧。"
                , "干的漂亮！家庭保障的意识真的很强呢，不过还是需要小保我来帮你把把关哦，毕竟专业的人做专业的事嘛！快把保单上传来我瞧瞧。"
                , "家庭保障欠缺哦！不过幸好你在茫茫人海中遇到了小保我，我会帮您建立起一套完整的家庭保障方案，为您降低风险，提高保障，这也是我存在的意义！"
        ),false
        ) {
            override
            fun getRespondsIndex(answersIndexs: HashSet<Int>): Int {
                if (answersIndexs.size == 0) return 2
                if (answersIndexs.contains(0)) return 2  //都没有
                if (!answersIndexs.contains(0) && answersIndexs.size == 1) return 0  //只有一个人
                if (answersIndexs.size >1 ) return 1  //超过一人
                else return 2
            }
        }



    }
}