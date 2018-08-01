package com.t_knight.and.capstone.model;

import java.util.List;

public class Topics {

    private List<TopicTitle> content;
    private List<Topic> topics;

    public List<TopicTitle> getContent() {
        return content;
    }

    public void setContent(List<TopicTitle> content) {
        this.content = content;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public class Topic {

        /**
         * id : 0
         * titleFrom : Язык тела
         * titleTo : Body language
         * source : ru
         * dest : eng
         * cardContent : [{"card":0,"to":"When we go for an interview, most of us think carefully about what to wear and what to say but hardly ever about how to act \u2013 in other words, what our body language is telling the interviewer.","from":"Когда мы идем на интервью, большинство из нас думают [внимательно] о том, что одеть и что говорить, но едва ли о том, как себя вести другими словами - что наш язык тела говорит интервьюеру."},{"card":1,"to":"According to experts, body language accounts for 55% of the effect we have when communicating. Tone of voice accounts for 33% and words just 7% - so what you say matters much less then how you behave.","from":"По мнению экспертов, язык тела составляет 55% от эффекта, который мы создаем при общении. Тон голоса составляет 33%, а слова - всего лишь 7% - то что вы говорите значит гораздо меньше, чем то, как вы себя ведете."},{"card":2,"to":"Employers nowadays are cautious about the fast-talking interviewee but they look increasingly for their signs which will show a person`s character and ability \u2013 such as body language.","from":"Работодатели в настоящее время настороженно относятся к быстро говорящему собеседнику и они все чаще смотрят на знаки, демонстрирующие характер и способности человека - такие как язык тела."},{"card":3,"to":"You should always smile when you enter the interview room and when the interview has finished because first and last impressions count.","from":"Вам следует всегда улыбаться, когда вы входите в комнату для интервью и когда собеседование завершается, потому что запоминается первое и последнее впечатление."},{"card":4,"to":"Moreover, you should also try to maintain eye-contact with the interviewer but not for too long.","from":"Более того, вам также следует стараться поддерживать зрительный контакт с интервьюером, но не слишком долго."},{"card":5,"to":"Once you`re sitting down, your hands should generally stay loosely in your lap. Use them to make a point occasionally but never raise them above shoulder level.","from":"Как только вы сели, ваши руки должны оставаться на ваших коленях расслаблеными. Используйте их время от времени, чтобы привлечь внимание, но никогда не поднимайте выше уровня плеча."},{"card":6,"to":"In fact, body language is vital. So, at an interview, take the trouble to get it right.","from":"На самом деле, язык тела крайне важен. Поэтому на собеседовании приложите усилия, чтобы быть правильно понятым."}]
         */

        private int id;
        private String titleFrom;
        private String titleTo;
        private String source;
        private String dest;
        private List<SingleCard> cardContent;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitleFrom() {
            return titleFrom;
        }

        public void setTitleFrom(String titleFrom) {
            this.titleFrom = titleFrom;
        }

        public String getTitleTo() {
            return titleTo;
        }

        public void setTitleTo(String titleTo) {
            this.titleTo = titleTo;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getDest() {
            return dest;
        }

        public void setDest(String dest) {
            this.dest = dest;
        }

        public List<SingleCard> getCardContent() {
            return cardContent;
        }

        public void setCardContent(List<SingleCard> cardContent) {
            this.cardContent = cardContent;
        }

    }
}
