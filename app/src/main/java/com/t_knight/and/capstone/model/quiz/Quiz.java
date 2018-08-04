package com.t_knight.and.capstone.model.quiz;

import java.util.List;

public class Quiz {
    /**
     * topic_id : 0
     * version : 1
     * cards : [{"id":0,"text":"When we go for an interview, most of us think carefully about what to wear and what to say but hardly ever about how to act \u2013 in other words, what our body language is telling the interviewer.","translation":"Когда мы идем на интервью, большинство из нас думают о том, что надеть и что говорить.","literal":"","spots":[{"start":0,"end":4,"answer":"When","answers":[],"hint_prefix":"","hint":"когда","hint_suffix":""},{"start":8,"end":10,"answer":"we","answers":[],"hint_prefix":"мы ","hint":"идем","hint_suffix":""},{"start":18,"end":27,"answer":"interview","answers":[],"hint_prefix":"","hint":"интервью","hint_suffix":""},{"start":87,"end":90,"answer":"say","answers":[],"hint_prefix":"","hint":"говорить","hint_suffix":""}]},{"id":1,"text":"According to experts, body language accounts for 55% of the effect we have when communicating.","translation":"","literal":"","spots":[{"start":0,"end":12,"answer":"According","answers":[],"hint_prefix":"","hint":"согласно","hint_suffix":"по мнению"},{"start":13,"end":20,"answer":"experts","answers":[],"hint_prefix":"","hint":"эксперты","hint_suffix":""},{"start":36,"end":44,"answer":"accounts","answers":[],"hint_prefix":"it","hint":"составляет","hint_suffix":""},{"start":60,"end":66,"answer":"effect","answers":[],"hint_prefix":"","hint":"эффект","hint_suffix":""}]},{"id":2,"text":"Tone of voice accounts for 33% and words just 7% - so what you say matters much less then how you behave.","translation":"","literal":"","spots":[{"start":0,"end":4,"answer":"Tone","answers":[],"hint_prefix":"","hint":"тон","hint_suffix":""},{"start":8,"end":13,"answer":"voice","answers":[],"hint_prefix":"","hint":"голос","hint_suffix":""},{"start":41,"end":45,"answer":"just","answers":[],"hint_prefix":"","hint":"всего лишь, только","hint_suffix":""},{"start":98,"end":104,"answer":"behave","answers":[],"hint_prefix":"inf","hint":"вести себя (поведение)","hint_suffix":""}]},{"id":3,"text":"Employers nowadays are cautious about the fast-talking interviewee but they look increasingly for their signs which will show a person`s character and ability \u2013 such as body language.","translation":"","literal":"","spots":[{"start":0,"end":8,"answer":"Employers","answers":[],"hint_prefix":"","hint":"работодатели","hint_suffix":""},{"start":42,"end":54,"answer":"fast-talking","answers":["fast talking"],"hint_prefix":"","hint":"быстро говорящий","hint_suffix":""},{"start":81,"end":93,"answer":"increasingly","answers":[],"hint_prefix":"","hint":"все чаще, все больше)","hint_suffix":"cont."},{"start":151,"end":158,"answer":"ability","answers":[],"hint_prefix":"","hint":"способность","hint_suffix":""}]},{"id":4,"text":"You should always smile when you enter the interview room and when the interview has finished because first and last impressions count.","translation":"","literal":"","spots":[{"start":11,"end":17,"answer":"always","answers":[],"hint_prefix":"","hint":"всегда","hint_suffix":""},{"start":33,"end":38,"answer":"enter","answers":[],"hint_prefix":"inf.","hint":"входить","hint_suffix":""},{"start":81,"end":93,"answer":"has finished","answers":["finished"],"hint_prefix":"to have + inf.","hint":"закончилось","hint_suffix":""},{"start":117,"end":128,"answer":"impression","answers":[],"hint_prefix":"","hint":"впечатление","hint_suffix":""}]},{"id":5,"text":"Moreover, you should also try to maintain eye-contact with the interviewer but not for too long.","translation":"","literal":"","spots":[{"start":0,"end":8,"answer":"Moreover","answers":[],"hint_prefix":"","hint":"более того","hint_suffix":""},{"start":33,"end":41,"answer":"maintain","answers":[],"hint_prefix":"","hint":"поддерживать","hint_suffix":""},{"start":42,"end":53,"answer":"eye-contact","answers":["eye contact"],"hint_prefix":"","hint":"зрительный контакт","hint_suffix":""},{"start":91,"end":95,"answer":"long","answers":[],"hint_prefix":"","hint":"длинный","hint_suffix":""}]},{"id":6,"text":"Once you`re sitting down, your hands should generally stay loosely in your lap.","translation":"","literal":"","spots":[{"start":0,"end":4,"answer":"Once","answers":[],"hint_prefix":"","hint":"как только","hint_suffix":""},{"start":31,"end":36,"answer":"hands","answers":[],"hint_prefix":"","hint":"руки","hint_suffix":""},{"start":59,"end":66,"answer":"loosely","answers":[],"hint_prefix":"","hint":"расслабленный","hint_suffix":""},{"start":75,"end":78,"answer":"lap","answers":[],"hint_prefix":"","hint":"колено","hint_suffix":""}]},{"id":7,"text":"Use them to make a point occasionally but never raise them above shoulder level.","translation":"","literal":"","spots":[{"start":0,"end":3,"answer":"Use","answers":[],"hint_prefix":"","hint":"использовать","hint_suffix":""},{"start":12,"end":24,"answer":"make a point","answers":["to make a point"],"hint_prefix":"","hint":"привлечь внимание","hint_suffix":""},{"start":42,"end":47,"answer":"never","answers":[],"hint_prefix":"","hint":"никогда","hint_suffix":""},{"start":65,"end":73,"answer":"shoulder","answers":[],"hint_prefix":"","hint":"плечо","hint_suffix":""}]},{"id":8,"text":"In fact, body language is vital. So, at an interview, take the trouble to get it right.","translation":"","literal":"","spots":[{"start":0,"end":7,"answer":"In fact","answers":[],"hint_prefix":"","hint":"на самом деле","hint_suffix":""},{"start":26,"end":31,"answer":"vital","answers":[],"hint_prefix":"","hint":"крайне важный (жизненно важный)","hint_suffix":""},{"start":54,"end":70,"answer":"take the trouble","answers":["to take trouble","take trouble"],"hint_prefix":"","hint":"прилагать усилия","hint_suffix":""},{"start":74,"end":86,"answer":"get it right","answers":["get right","to get right"],"hint_prefix":"","hint":"быть правильно понятым","hint_suffix":""}]}]
     */

    private int topic_id;
    private int version;
    private List<QuizCard> cards;

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<QuizCard> getCards() {
        return cards;
    }

    public void setCards(List<QuizCard> cards) {
        this.cards = cards;
    }

}
