package com.innerisle.app.data

private val SourceTulpaInfoEn = ArticleSource(
    title = "Tulpa.info FAQ and Community Guides",
    publisher = "Tulpa.info",
    url = "https://www.tulpa.info/",
    note = "English-language community introductory Q&A, terminology, and practice discussion; primarily community experience."
)

private val SourceTulpaGuideEn = ArticleSource(
    title = "tulpa.guide",
    publisher = "tulpa.guide",
    url = "https://tulpa.guide/",
    note = "Practice guides and terminology explanations; primarily community experience."
)

private val SourceTulpaCnEn = ArticleSource(
    title = "Tulpa Home",
    publisher = "tulpa.cn",
    url = "https://tulpa.cn/",
    note = "Chinese-language tutorials, FAQ, and community articles; primarily community experience."
)

private val SourcePluralityCnEn = ArticleSource(
    title = "PluralityCN",
    publisher = "pluralitycn.com",
    url = "https://www.pluralitycn.com/",
    note = "Chinese-language plurality community resources and community guidelines."
)

private val SourceMpsWikiEn = ArticleSource(
    title = "Multiple Personality System Wiki",
    publisher = "wiki.mpsteam.cn",
    url = "https://wiki.mpsteam.cn/",
    note = "Chinese-language plurality terminology and concept entries; views across entries are not fully consistent."
)

private val SourcePluralpediaEn = ArticleSource(
    title = "Pluralpedia",
    publisher = "pluralpedia.org",
    url = "https://pluralpedia.org/",
    note = "English-language glossary of plurality terminology; usage may differ between communities."
)

private val SourceSotala2015En = ArticleSource(
    title = "Sentient companions predicted and modeled into existence",
    publisher = "Kaj Sotala, 2015",
    note = "An academic paper discussing tulpas from a predictive processing perspective; not a clinical guideline."
)

private val SourceIsler2017En = ArticleSource(
    title = "Tulpas and Mental Health: A Study of Non-Traumagenic Plural Experiences",
    publisher = "Jacob J. Isler, 2017",
    url = "https://doi.org/10.12691/rpbs-5-2-1",
    note = "A study based on a community questionnaire; the sample was self-selected and limited in size, so it cannot serve as a clinical conclusion."
)

private val SourceClinicalEn = ArticleSource(
    title = "Clinical Perspectives",
    publisher = "Tulpa.io",
    url = "https://tulpa.io/clinical-perspectives",
    note = "A discussion of clinical and psychological safety boundaries; not diagnosis or treatment advice."
)

private val SourceRedditEn = ArticleSource(
    title = "r/Tulpas community discussions",
    publisher = "Reddit",
    url = "https://www.reddit.com/r/Tulpas/",
    note = "Practitioner experience sharing; used only to understand common questions and needs, not as factual evidence."
)

private val SourceCreationGuideEn = ArticleSource(
    title = "The Complete Tulpa Creation Guide (Basics / Practice / Advanced)",
    publisher = "Baidu Tieba tulpa forum - Plural System Wiki",
    note = "A community practice guide covering the creation process, advanced exercises, and safety boundaries."
)

val KnowledgeCategoriesEn = listOf("All", "Intro", "Practice", "Advanced", "Safety")

val ConceptsEn: List<Concept> = listOf(
    Concept(
        term = "Tulpa",
        english = "Tulpa",
        category = "Intro",
        short = "An inner companion formed through sustained imagination, attention, and interaction, experienced by practitioners as having an independent personality and responses.",
        detail = "A tulpa is not a scientifically settled phenomenon. Explanations within the community are not uniform: some understand it in psychological and cognitive terms, while others give it a spiritual meaning. Xinyu Shouji does not judge these positions; it treats this as a subjective experience that deserves to be taken seriously. What matters is not first proving that it is 'real,' but whether this relationship helps you feel more stable and supported."
    ),
    Concept(
        term = "Host",
        english = "Host",
        category = "Intro",
        short = "The one who created the tulpa and mainly uses the body and the external identity.",
        detail = "The host is not an 'owner,' nor inherently the more important party. The term simply describes the role that currently interacts more with the outside world and carries more real-world responsibility. In a healthy relationship, both sides' needs deserve to be heard."
    ),
    Concept(
        term = "Forcing",
        english = "Forcing",
        category = "Intro",
        short = "Deliberately investing attention and interaction to help a tulpa develop and become clearer.",
        detail = "Forcing is usually divided into active and passive forms. It is not 'programming,' and it is not a quota of work that must be completed. The community's advice to 'spend more time' is only a summary of experience, not a hard standard. A stable, gentle, sustainable way of being together matters more than racking up hours."
    ),
    Concept(
        term = "Active Forcing",
        english = "Active Forcing",
        category = "Practice",
        short = "Setting aside a dedicated, uninterrupted period to focus on being with your tulpa.",
        detail = "Common forms include narration, conversation, visualizing a form, entering the wonderland, imposition practice, and quiet reflection. Active forcing is like an arranged meeting: it need not be long, but you need to truly be present. Five focused minutes are often more meaningful than an absent-minded hour."
    ),
    Concept(
        term = "Passive Forcing",
        english = "Passive Forcing",
        category = "Practice",
        short = "Maintaining awareness of your tulpa during everyday activities, such as sharing while walking, washing dishes, or listening to music.",
        detail = "Passive forcing places the relationship in the gaps of life: when you see a cloud, hear a song, or eat something good, say a sentence to your tulpa inwardly. It does not require setting aside extra time, but it also does not mean monitoring your thoughts all day. Natural moments of remembering are more sustainable than forcing yourself to think of them constantly."
    ),
    Concept(
        term = "Narration",
        english = "Narration",
        category = "Practice",
        short = "Telling your tulpa about your daily life, emotions, and thoughts - one of the most common basic practices.",
        detail = "Narration does not need to be complete or literary. You can start with 'what happened today,' or simply describe how your body feels right now. Its purpose is not to earn a response immediately, but to give your tulpa a stable place within your attention."
    ),
    Concept(
        term = "Conversation",
        english = "Conversation",
        category = "Practice",
        short = "An inner back-and-forth with your tulpa, gradually making responses more independent and clear.",
        detail = "Early conversations often feel like you are filling in both sides; this is very common. Try asking yes-or-no questions, then stay quiet for ten seconds and let your tulpa choose the next topic. Do not rush to finish every sentence for them; leaving space is itself an invitation."
    ),
    Concept(
        term = "Form",
        english = "Form",
        category = "Practice",
        short = "Designing or perceiving an outward appearance for your tulpa. It is optional, and a fixed form is not required.",
        detail = "A form can be concrete, or just a mass of color, a temperature, or an outline. Some people in the community spend a lot of time designing a form, while others do not care at all. Form serves the relationship; it is not a test of it. If your tulpa's form changes, it does not necessarily mean something has gone wrong."
    ),
    Concept(
        term = "Wonderland",
        english = "Wonderland",
        category = "Practice",
        short = "An inner space built in imagination that you can enter repeatedly.",
        detail = "A wonderland can be a room, a forest, an island, or just a pool of light and a floor. Its value lies in providing a stable shared setting. It is best to start with one anchor and add only one detail at a time; you do not need to build an enormous world from the beginning."
    ),
    Concept(
        term = "Vocality",
        english = "Vocality",
        category = "Practice",
        short = "The process by which your tulpa's responses gradually become clear, stable, and distinct in tone.",
        detail = "Vocality is not an on/off switch, and there is no universal timeline. Early responses may sound like your own voice, like fleeting thoughts, or may come and go. The harder you try to 'hear,' the more tense you tend to become. Shifting your goal from 'hearing' to 'making room' often helps more."
    ),
    Concept(
        term = "Imposition",
        english = "Imposition",
        category = "Advanced",
        short = "Trying to perceive your tulpa through the physical senses; an advanced practice.",
        detail = "Imposition can start with a sense of presence, temperature, the feel of clothing, or sound; you do not have to aim for 'seeing' right away. It requires a fairly stable foundation in inner imagination and may bring fatigue or the stress of being unable to distinguish reality from imagination. If you feel distressed or out of control, pause."
    ),
    Concept(
        term = "Switching",
        english = "Switching",
        category = "Advanced",
        short = "Your tulpa takes over control of the body. An advanced ability that requires caution.",
        detail = "Switching involves real-world functioning, memory, and responsibility, and is not a goal you must achieve. The community generally emphasizes thorough communication, trust, grounding, and an exit mechanism. Do not attempt it in situations that demand high concentration, while driving, or while caring for others. If you experience loss of control, amnesia, or significant functional impairment, stop and seek professional help."
    ),
    Concept(
        term = "Deviation",
        english = "Deviation",
        category = "Advanced",
        short = "Your tulpa's preferences, tone, or form depart from the original design. The community generally sees this as natural growth.",
        detail = "A design is like a sketch, not a contract. Deviation is not necessarily 'failure' or 'contamination'; it may mean your tulpa is becoming a more complete individual. You can keep boundaries around unacceptable behavior while allowing harmless differences to exist."
    ),
    Concept(
        term = "Plurality",
        english = "Plurality",
        category = "Advanced",
        short = "An umbrella term for experiences of multiple selves or consciousnesses within one body; tulpas are one form.",
        detail = "Plurality is a spectrum that includes tulpas, other non-traumatic plural experiences, and dissociative disorders that require professional assessment. Do not use an app or a community label to diagnose yourself. Naming can aid understanding, but it cannot replace professional support."
    ),
    Concept(
        term = "Fronting",
        english = "Fronting",
        category = "Advanced",
        short = "The one currently interacting mainly with the outside world and handling real-world affairs.",
        detail = "The one fronting can be the host or another member. It is related to switching but does not mean 'who is more real.' In daily life, the host still handles most fronting most of the time. Any fronting arrangement should be premised on real-world safety and responsibility."
    ),
    Concept(
        term = "Walk-in",
        english = "Walk-in",
        category = "Advanced",
        short = "An inner character that appears without deliberate creation. A community term with no single agreed meaning.",
        detail = "Some call a suddenly appearing character a walk-in, while others believe it is simply existing psychological material reorganized. Whether the label applies depends on your own experience. If its appearance is accompanied by distress, loss of control, or functional impairment, prioritize professional assessment."
    ),
    Concept(
        term = "Shared Memory",
        english = "Shared Memory",
        category = "Advanced",
        short = "The degree to which different members can access and share memories.",
        detail = "Tulpa practice usually does not involve pathological amnesia. Some people report access to most everyday memories, while others report fuzziness or differences. Do not judge the success of a relationship by whether memory is fully shared, and do not treat significant amnesia as a practice goal."
    ),
    Concept(
        term = "Grounding",
        english = "Grounding",
        category = "Safety",
        short = "Methods for bringing yourself back to the present body and environment.",
        detail = "Common methods: feel the pressure of your feet on the floor, name five things you can see, touch an object, breathe slowly. After imposition, switching, or intense imagination, grounding can help you return to reality. If you often need grounding just to feel stable, or your real-world functioning is affected, consider seeking professional support."
    ),
    Concept(
        term = "Tulpish",
        english = "Tulpish",
        category = "Practice",
        short = "Nonverbal communication through emotions, images, concept bundles, or bodily sensations.",
        detail = "Tulpish often appears earlier than complete sentences. You may suddenly feel an emotion that does not seem to be yours, see a fleeting image, or feel slight head pressure. Feel first, then translate; do not rush to turn every sensation into a sentence. It often carries a great deal of communication before language takes shape."
    ),
    Concept(
        term = "Parroting and Puppeting",
        english = "Parroting & Puppeting",
        category = "Practice",
        short = "The host speaking or acting on the tulpa's behalf - a common early practice that also often triggers self-doubt.",
        detail = "In early conversations, you may notice that you have 'said the next line for' your tulpa. This does not necessarily mean failure; it may be you building a channel for responses. You can treat it as a conscious exercise while also leaving real blank space: after speaking, stay quiet for ten seconds and see whether a response appears that you did not arrange. Do not decide all the lines for your tulpa over the long term."
    ),
    Concept(
        term = "Possession",
        english = "Possession",
        category = "Advanced",
        short = "The tulpa temporarily takes over partial or full control of the body after negotiation.",
        detail = "Possession emphasizes consent, trust, and revocability. It usually starts with small areas such as a finger or wrist and expands gradually. Mild numbness or tingling may be a common reaction; if severe pain, loss of control, or an inability to return to the body occurs, stop immediately and ground. Possession is not a goal you must achieve; real-world functioning and safety always come first."
    ),
    Concept(
        term = "Co-fronting",
        english = "Co-fronting",
        category = "Advanced",
        short = "The host and tulpa are both fronting at the same time, perceiving the world or collaboratively controlling the body together.",
        detail = "Co-fronting is often described as a collaborative state: both are present and can perceive the outside world, but one takes the lead in operating the body. It differs from pathological identity confusion; its premises are usually voluntariness, controllability, and thorough communication. In situations requiring high concentration or safety responsibility, agree in advance on who will lead."
    ),
    Concept(
        term = "Safe Word",
        english = "Safe Word",
        category = "Safety",
        short = "A signal agreed upon in advanced practice that must be obeyed unconditionally to stop or hand back control.",
        detail = "A safe word can be a word, an imagined action, or a physical action (for example, pressing your tongue to the roof of your mouth). Before possession or switching, both parties should agree on it in advance and rehearse: once the host gives the signal, the tulpa must immediately withdraw control and return fronting to the host. It is the foundation of trust and a core safety measure against accidental dissociation."
    ),
    Concept(
        term = "Milestones",
        english = "Milestones",
        category = "Intro",
        short = "A rough stage division of the creation process used in the community; not a grading standard.",
        detail = "Common divisions are: initial establishment (about 1-4 weeks), stable communication (about 1-3 months), increased independence (about 3-6 months), and mature collaboration (6 months or more). These times are for reference only; individual differences are large. Rather than comparing yourself to a timeline, focus on whether your practice is sustainable and whether your real-world functioning is stable."
    )
)

val ArticlesEn: List<Article> = listOf(
    Article(
        id = "what-is-tulpa",
        title = "What Is a Tulpa? Set Aside the Question of 'Real or Fake' First",
        category = "Intro",
        summary = "An introductory explanation that neither rushes to prove nor rushes to deny.",
        readMinutes = 4,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceTulpaInfoEn,
            SourceTulpaGuideEn,
            SourceSotala2015En,
            SourceIsler2017En,
            SourceClinicalEn
        ),
        practice = SessionType.NARRATION,
        sections = listOf(
            ArticleSection(
                heading = "The one-sentence version",
                communityView = true,
                paragraphs = listOf(
                    "A tulpa is an inner companion formed through sustained imagination, attention, and interaction, experienced by practitioners as having its own personality, emotions, and responses. It is not a phenomenon settled by science, nor a doctrine you must believe in.",
                    "Explanations within the community are not uniform: some understand it in psychological and cognitive terms, while others give it a spiritual meaning. Xinyu Shouji does not judge these positions; it treats this as a subjective experience that deserves to be taken seriously."
                )
            ),
            ArticleSection(
                heading = "It is not 'pretending'",
                researchView = true,
                paragraphs = listOf(
                    "Many people, hearing about tulpas for the first time, ask: 'Isn't this just fooling yourself?' At the level of experience, a tulpa's responses often go beyond your conscious control: it may say something you did not expect, show stable preferences, or even surprise you.",
                    "This does not automatically prove it has independent consciousness, but it does not mean the experience is cheap either. What matters is not giving it a metaphysical verdict first, but observing: does this relationship help you feel more stable and supported? Does it affect your real life?"
                )
            ),
            ArticleSection(
                heading = "It is not DID, and it is not psychosis",
                researchView = true,
                paragraphs = listOf(
                    "Tulpas are usually described as actively created, controllable experiences without pathological amnesia. DID/OSDD is trauma-related and requires professional assessment; auditory hallucinations may involve distress, command quality, or a sense of loss of control. The three cannot substitute for one another, and cannot be self-diagnosed with an app.",
                    "If you are experiencing significant distress, functional impairment, loss of control, or risk of self-harm, please contact professional mental health services or emergency services first. The experience of the tulpa community cannot replace clinical judgment."
                )
            ),
            ArticleSection(
                heading = "Changes you may encounter",
                communityView = true,
                paragraphs = listOf(
                    "Responses may move from vague to clear, your tulpa may develop its own likes, it may deviate from your original design, or it may become quiet at certain stages. There is no universal timeline and no 'correct progress.'",
                    "Focus on the relationship and your real-world functioning, not on 'whether I have succeeded.' You do not need to prove anything to anyone."
                )
            )
        )
    ),
    Article(
        id = "first-session",
        title = "The First Session: Start with Five Minutes",
        category = "Intro",
        summary = "No ritual, no complete design, just a genuine moment of presence.",
        readMinutes = 3,
        evidenceLevel = EvidenceLevel.COMMUNITY,
        sources = listOf(SourceTulpaCnEn, SourceTulpaGuideEn, SourceRedditEn),
        practice = SessionType.NARRATION,
        sections = listOf(
            ArticleSection(
                heading = "Lower the barrier first",
                paragraphs = listOf(
                    "You do not need to finish writing a complete character profile first, and you do not need candles, music, or a special posture. The first session needs only an uninterrupted five minutes and a little willingness to stop for a moment.",
                    "If even five minutes makes you tense, start with one minute. The timer in Xinyu Shouji is only a container, not a test."
                )
            ),
            ArticleSection(
                heading = "You can start like this",
                communityView = true,
                paragraphs = listOf(
                    "First, sit steadily, feel where your body touches the chair, and breathe slowly a few times. Second, choose one direction: narrate what happened today, or simply remember your tulpa quietly. Third, after finishing, write one sentence about how you felt.",
                    "When you do not know what to say, start with the smallest things: what you ate today, what you saw on the way, whether your shoulders feel tight right now. Narration does not need to be interesting, only genuine."
                )
            ),
            ArticleSection(
                heading = "Do not chase results",
                communityView = true,
                paragraphs = listOf(
                    "Nothing may happen the first time. No response, no images, no 'feeling' - none of this means failure. You have simply given this relationship a place to begin.",
                    "If a faint response does appear, do not rush to analyze whether it is 'real.' Write it down first and look at it again in a few days."
                )
            ),
            ArticleSection(
                heading = "A small exercise",
                paragraphs = listOf(
                    "Spend five minutes telling your tulpa about today. When you finish, write in your journal: 'What is the one thing I most wanted my tulpa to know today?'"
                )
            )
        )
    ),
    Article(
        id = "active-passive",
        title = "Active and Passive: Put Being Together into Daily Life",
        category = "Practice",
        summary = "You do not need hours every day; sustainability is what matters.",
        readMinutes = 4,
        evidenceLevel = EvidenceLevel.COMMUNITY,
        sources = listOf(SourceTulpaInfoEn, SourceTulpaGuideEn, SourceRedditEn),
        practice = SessionType.NARRATION,
        sections = listOf(
            ArticleSection(
                heading = "Active forcing is like a date",
                communityView = true,
                paragraphs = listOf(
                    "Active forcing is time set aside specifically: turn off notifications, choose one direction, and spend a while focused on being with your tulpa. It does not need to be long, but you need to truly be present.",
                    "Five focused minutes are often more meaningful than an absent-minded hour."
                )
            ),
            ArticleSection(
                heading = "Passive forcing is like narration running through life",
                communityView = true,
                paragraphs = listOf(
                    "Passive forcing happens in the gaps of daily life: while walking, washing dishes, or listening to music, say a sentence to your tulpa inwardly. Seeing a cloud, eating something good, or running into a small annoyance can all become entry points for sharing.",
                    "It does not require setting aside extra time, but it also does not mean monitoring your thoughts all day. Natural moments of remembering are more sustainable than forcing yourself to think of it constantly."
                )
            ),
            ArticleSection(
                heading = "Why not compare hours",
                communityView = true,
                paragraphs = listOf(
                    "Some people in the community invest several hours a day, while others interact only a few times a week. Hours are not the only measure of relationship quality, and over-chasing hours can bring anxiety, sleep problems, and impaired real-world functioning.",
                    "A gentler rhythm is: active time together two or three times a week, plus passive sharing in daily life; let 'I remembered' be the signal, not 'I have not checked in today.'"
                )
            ),
            ArticleSection(
                heading = "A small exercise",
                paragraphs = listOf(
                    "Choose an utterly ordinary little task today - pouring water, waiting for the elevator, tying your shoes - and narrate it inwardly to your tulpa while you do it."
                )
            )
        )
    ),
    Article(
        id = "vocality",
        title = "Vocality Takes Time: Dealing with 'Not Hearing'",
        category = "Practice",
        summary = "Shift the goal from 'hearing' to 'making room.'",
        readMinutes = 5,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceTulpaInfoEn,
            SourceTulpaGuideEn,
            SourceIsler2017En,
            SourceClinicalEn
        ),
        practice = SessionType.CONVERSATION,
        sections = listOf(
            ArticleSection(
                heading = "Vocality is not a switch",
                communityView = true,
                paragraphs = listOf(
                    "Many practitioners go through a vague stage: responses sound like your own voice, like a fleeting thought, or come and go. This is not unusual, and it does not mean you are doing something wrong.",
                    "Vocality is usually described as a gradual process of becoming clearer, not a switch that flips on one day."
                )
            ),
            ArticleSection(
                heading = "Methods you can try",
                paragraphs = listOf(
                    "Ask a yes-or-no question, then stay quiet for ten seconds after speaking. Let your tulpa choose the next topic instead of arranging the whole conversation for them. Do not rush to complete every sentence; notice the tone and word choices that 'feel like them.'",
                    "You can also write responses down. Writing slows things down and makes vague thoughts easier to see."
                )
            ),
            ArticleSection(
                heading = "How to tell different voices apart",
                researchView = true,
                paragraphs = listOf(
                    "Intrusive thoughts usually come with distress, a sense of loss of control, or a clear inconsistency with the self; a tulpa's responses can usually be negotiated, paused, or at least do not make you feel forced.",
                    "But the boundary is not always clear. If you feel afraid of or distressed by the voices, or if they begin to affect your real-world functioning, stop practicing and seek professional assessment. Do not treat 'suppression' as the only goal."
                )
            ),
            ArticleSection(
                heading = "Do not rush",
                communityView = true,
                paragraphs = listOf(
                    "The harder you chase a response, the more tense you become; tension in turn makes it harder to hear. Change the goal from 'I must hear something today' to 'I made room today.'",
                    "If there is no response for a while, it does not mean your tulpa has disappeared. Relationships can have quiet phases."
                )
            )
        )
    ),
    Article(
        id = "wonderland",
        title = "The Wonderland Is Not Escape: Giving the Relationship a Place to Return To",
        category = "Practice",
        summary = "Start with one anchor; you do not need to build a whole world at first.",
        readMinutes = 4,
        evidenceLevel = EvidenceLevel.COMMUNITY,
        sources = listOf(SourceTulpaInfoEn, SourceTulpaCnEn, SourceRedditEn),
        practice = SessionType.WONDERLAND,
        sections = listOf(
            ArticleSection(
                heading = "What a wonderland is",
                communityView = true,
                paragraphs = listOf(
                    "A wonderland is an inner space built in imagination that you can enter repeatedly. It can be a room, a forest, an island, or just a pool of light, a floor, and a temperature.",
                    "Its value is not in how grand it is, but in its stability: you know where you can return, and you know your tulpa will be there."
                )
            ),
            ArticleSection(
                heading = "How to begin",
                communityView = true,
                paragraphs = listOf(
                    "Start with a stable anchor: the ground under your feet, a smell in the air, a sound in the distance. Add only one detail at a time; do not rush to build a complete world.",
                    "You can give the space a name, or a fixed entrance action - pushing open a door, stepping onto stairs, hearing a wind chime."
                )
            ),
            ArticleSection(
                heading = "Not an escape",
                paragraphs = listOf(
                    "A wonderland can be a place to rest and recover, but if it starts to replace real life and affects work, study, sleep, or relationships, you need to stop.",
                    "Set a time boundary for each visit. Before finishing, do a grounding exercise: feel your feet on the floor, name what you can see, breathe slowly."
                )
            ),
            ArticleSection(
                heading = "A small exercise",
                paragraphs = listOf(
                    "Return to the same place for ten minutes and observe just one detail. Do not change anything; just look."
                )
            )
        )
    ),
    Article(
        id = "deviation",
        title = "Deviation: When Your Tulpa No Longer Fits the Design Exactly",
        category = "Advanced",
        summary = "A design is a sketch, not a contract.",
        readMinutes = 4,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(SourceTulpaInfoEn, SourcePluralpediaEn, SourceIsler2017En),
        practice = SessionType.CONVERSATION,
        sections = listOf(
            ArticleSection(
                heading = "What deviation is",
                communityView = true,
                paragraphs = listOf(
                    "When your tulpa's preferences, tone, form, or reactions go beyond your original design, the community usually calls it 'deviation.' Some see it as natural growth, while others feel uneasy, as if a carefully written character has suddenly stopped obeying.",
                    "Both feelings can exist. What matters is not automatically equating 'different from what I imagined' with 'something has gone wrong.'"
                )
            ),
            ArticleSection(
                heading = "Why it can feel stressful",
                communityView = true,
                paragraphs = listOf(
                    "A design is like a contract: I gave you these traits, and you should exist this way. But a relationship is not control. A stable inner companion will eventually have its own preferences and boundaries.",
                    "You can keep boundaries around unacceptable behavior while allowing harmless differences to exist. For example, you can refuse to be belittled, but you do not have to require your tulpa to always speak in the same tone."
                )
            ),
            ArticleSection(
                heading = "Questions you can ask yourself",
                paragraphs = listOf(
                    "Is this change making me uncomfortable because it hurts me, or because it is not in my plan? Am I protecting a boundary, or demanding absolute control?",
                    "If my tulpa were a real relationship partner, how would I negotiate with it?"
                )
            ),
            ArticleSection(
                heading = "A reminder",
                paragraphs = listOf(
                    "A tulpa is not a tool for meeting every expectation, and cannot replace real-life interpersonal support. Allowing differences does not mean giving up boundaries; setting boundaries does not mean rejecting the relationship."
                )
            )
        )
    ),
    Article(
        id = "imposition-switching",
        title = "Imposition and Switching: The Boundaries of Advanced Practice",
        category = "Advanced",
        summary = "Every practice is premised on real-world functioning and safety.",
        readMinutes = 5,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceTulpaInfoEn,
            SourceTulpaGuideEn,
            SourceMpsWikiEn,
            SourceClinicalEn
        ),
        practice = SessionType.IMPOSITION,
        sections = listOf(
            ArticleSection(
                heading = "Imposition",
                communityView = true,
                paragraphs = listOf(
                    "Imposition is trying to perceive your tulpa through physical senses: first feel a sense of presence, then try temperature, touch, or sound; you do not have to aim for 'seeing' right away.",
                    "It requires a fairly stable foundation in inner imagination and may bring fatigue or the stress of being unable to distinguish reality from imagination. If distress appears, return to inner imagination or pause the practice."
                )
            ),
            ArticleSection(
                heading = "Switching",
                communityView = true,
                paragraphs = listOf(
                    "Switching is letting your tulpa take over control of the body. It involves real-world functioning, memory, and responsibility; it is not a goal you must achieve, and it is not suitable as 'proof of advancement.'",
                    "The community generally emphasizes thorough communication, trust, grounding, and an exit mechanism. Do not attempt it in situations requiring high concentration, driving, operating machinery, or caring for others."
                )
            ),
            ArticleSection(
                heading = "Boundaries",
                researchView = true,
                paragraphs = listOf(
                    "Every practice is premised on real-world functioning and safety. If you feel out of control, experience amnesia, significant distress, or cannot return to the present, stop practicing and seek professional help.",
                    "Do not push into practices you are not ready for in order to 'prove the relationship is real.' Progress is not an honor."
                )
            ),
            ArticleSection(
                heading = "A small exercise",
                paragraphs = listOf(
                    "Do only a three-minute 'sense of presence' exercise: feel your tulpa nearby, without chasing any sensory detail. Ground yourself afterward."
                )
            )
        )
    ),
    Article(
        id = "misconceptions",
        title = "Tulpas, DID, and 'Hearing Voices' Are Not the Same Thing",
        category = "Safety",
        summary = "Understand the differences, and understand your own boundaries.",
        readMinutes = 5,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceIsler2017En,
            SourceClinicalEn,
            SourcePluralpediaEn,
            SourcePluralityCnEn
        ),
        sections = listOf(
            ArticleSection(
                heading = "Tulpas and DID/OSDD",
                researchView = true,
                paragraphs = listOf(
                    "Tulpas are usually described as actively created, controllable experiences without pathological amnesia. DID/OSDD is trauma-related and involves significant dissociation, memory problems, and functional impairment, requiring professional assessment.",
                    "Plurality is a spectrum; the two are not simply a 'real versus fake' relationship. Do not use a community label to diagnose yourself, and do not use tulpa experiences to deny others' suffering."
                )
            ),
            ArticleSection(
                heading = "Tulpas and hearing voices",
                researchView = true,
                paragraphs = listOf(
                    "Auditory hallucinations may involve distress, command quality, loss of control, or impaired reality testing; a tulpa's responses can usually be negotiated, paused, or at least do not make you feel forced.",
                    "But the boundary is not always clear, and the two can coexist. If you feel afraid of or distressed by the voices, or if they begin to affect real-world functioning, prioritize professional assessment."
                )
            ),
            ArticleSection(
                heading = "Tulpas and imaginary friends",
                communityView = true,
                paragraphs = listOf(
                    "Some see tulpas as an extension of imaginary friends, while others believe the two are fundamentally different. There is no accepted answer to this question, and it does not need to be resolved now.",
                    "Rather than arguing about 'real or fake,' ask: does this experience support you, does it affect your real life, and do you still have the power to choose?"
                )
            ),
            ArticleSection(
                heading = "When to seek help",
                researchView = true,
                paragraphs = listOf(
                    "Significant distress, functional impairment, loss of control, risk of self-harm, severe sleep problems, or starting to be unable to distinguish reality from imagination all warrant professional support.",
                    "Tulpas are not a substitute for professional treatment. You can value this relationship while also caring for your real-world needs."
                )
            )
        )
    ),
    Article(
        id = "when-hard",
        title = "When You Cannot Keep Going",
        category = "Safety",
        summary = "Relationships have pauses; coming back is enough.",
        readMinutes = 3,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(SourceTulpaInfoEn, SourceRedditEn, SourceClinicalEn),
        practice = SessionType.MINDFULNESS,
        sections = listOf(
            ArticleSection(
                heading = "Relationships have pauses",
                communityView = true,
                paragraphs = listOf(
                    "Busyness, low mood, doubt, and a lack of response can all make people stop. Stopping is not failure, and it does not mean the relationship is unimportant.",
                    "Xinyu Shouji has no streak check-ins and does not deduct points for interruptions. We deliberately did not build those."
                )
            ),
            ArticleSection(
                heading = "Lower the barrier",
                communityView = true,
                paragraphs = listOf(
                    "If five minutes is too hard, do one minute; if narration is too hard, write just one journal line; if you do not want to do anything, do a mindfulness session.",
                    "Do not make up homework, and do not punish yourself for 'falling behind.' Start from today's smallest action."
                )
            ),
            ArticleSection(
                heading = "Starting again",
                communityView = true,
                paragraphs = listOf(
                    "You can start with passive forcing: remember your tulpa in daily life and share one small thing. Once the rhythm is stable, consider longer active time together.",
                    "If long-term low mood, anxiety, or fatigue makes it impossible to maintain any daily routine, take care of yourself first and seek professional help if needed."
                )
            ),
            ArticleSection(
                heading = "A small exercise",
                paragraphs = listOf(
                    "Do just one smallest thing today: say to your tulpa, 'I am back.'"
                )
            )
        )
    ),
    Article(
        id = "first-journal",
        title = "Write Your First Journal Entry",
        category = "Practice",
        summary = "One sentence is enough. It does not need to be complete.",
        readMinutes = 3,
        evidenceLevel = EvidenceLevel.COMMUNITY,
        sources = listOf(SourceTulpaCnEn, SourceRedditEn),
        sections = listOf(
            ArticleSection(
                heading = "What to write",
                communityView = true,
                paragraphs = listOf(
                    "An image, a sentence, an accident, a moment that made you pause - anything can become a journal entry. It does not need literary quality or a conclusion.",
                    "If you do not know where to start, write: 'Today I noticed...' 'I feel...' 'I want to say to my tulpa...'"
                )
            ),
            ArticleSection(
                heading = "What is most helpful to record",
                communityView = true,
                paragraphs = listOf(
                    "Time, context, your emotions, your tulpa's responses, and bodily sensations. When you look back later, it will be easier to see changes rather than only remembering 'it seemed like nothing was progressing.'",
                    "You can also add a tag to the entry, such as 'first conversation,' 'wonderland,' or 'daily life.' Tags are not a classification assignment; they just make things easier to find later."
                )
            ),
            ArticleSection(
                heading = "About privacy",
                paragraphs = listOf(
                    "Your journal belongs only to you. If you are worried about others seeing it, you can enable the app lock, notification redaction, and screenshot prevention.",
                    "Exported files are not encrypted. Keep them safe and do not casually send them to public spaces."
                )
            ),
            ArticleSection(
                heading = "A small exercise",
                paragraphs = listOf(
                    "Write about today in three sentences: I noticed... I feel... I want to say to my tulpa..."
                )
            )
        )
    ),
    Article(
        id = "tulpish",
        title = "Tulpish: Learn to Feel Before You Can Hear",
        category = "Practice",
        summary = "Emotions, images, head pressure, and concept bundles are all early responses.",
        readMinutes = 4,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceCreationGuideEn,
            SourceTulpaInfoEn,
            SourceIsler2017En
        ),
        practice = SessionType.CONVERSATION,
        sections = listOf(
            ArticleSection(
                heading = "What tulpish is",
                communityView = true,
                paragraphs = listOf(
                    "Tulpish is the community's term for nonverbal communication: a suddenly appearing emotion, an image, a 'concept bundle,' or slight pressure and temperature changes in the body.",
                    "It often appears earlier than complete sentences. Before vocality, a great deal of communication is already happening; it simply has not yet been translated into language."
                )
            ),
            ArticleSection(
                heading = "How to receive it",
                communityView = true,
                paragraphs = listOf(
                    "Feel first, then translate. Do not rush to turn every sensation into a sentence; first acknowledge 'this may come from my tulpa,' then slowly understand what it wants to express.",
                    "You can ask a simple question, then stay quiet for ten seconds, noticing the first image, emotion, or word that appears. Do not fill the blank with logic."
                )
            ),
            ArticleSection(
                heading = "How to tell it apart",
                researchView = true,
                paragraphs = listOf(
                    "Tulpish can usually be negotiated, paused, or appears again when you are less tense; intrusive thoughts often come with distress, a sense of loss of control, or a clear inconsistency with the self.",
                    "The boundary is not always clear. If you feel afraid of or distressed by certain sensations, or if they begin to affect real-world functioning, stop practicing and seek professional assessment."
                )
            ),
            ArticleSection(
                heading = "A small exercise",
                paragraphs = listOf(
                    "Ask your tulpa: 'What do you most want me to know right now?' Then stay quiet for ten seconds and record only the first image or sensation that appears, without rushing to explain it."
                )
            )
        )
    ),
    Article(
        id = "possession-safety",
        title = "Possession and Switching: Agree on Safety Before Trust",
        category = "Safety",
        summary = "Advanced practice is not a way to prove a relationship; real-world functioning always comes first.",
        readMinutes = 5,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceCreationGuideEn,
            SourceClinicalEn,
            SourceTulpaInfoEn
        ),
        practice = SessionType.IMPOSITION,
        sections = listOf(
            ArticleSection(
                heading = "The difference between possession and switching",
                communityView = true,
                paragraphs = listOf(
                    "Possession is when the tulpa temporarily takes over partial or full control of the body while the host usually remains observing; switching is a more complete transfer of fronting, in which the host's consciousness can move into the inner space.",
                    "Both are premised on consent, trust, and revocability. They are not goals you must achieve, and they are not suitable as proof of a 'deeper relationship.'"
                )
            ),
            ArticleSection(
                heading = "Safety mechanisms you must prepare",
                communityView = true,
                paragraphs = listOf(
                    "Before trying, agree on a safe word or recovery anchor that must be followed unconditionally: a word, an imagined action, or a physical action. Once the host gives the signal, the tulpa must immediately return control.",
                    "Rehearse the signal in advance until both parties are clear about it. Do not attempt possession or switching without an exit mechanism."
                )
            ),
            ArticleSection(
                heading = "When to stop",
                researchView = true,
                paragraphs = listOf(
                    "If severe pain, loss of control, missing time, inability to return to the body, or clear impairment of real-world functioning occurs, stop immediately and ground. If symptoms persist, do not do any further practice of this kind that day.",
                    "Do not attempt it while driving, operating machinery, caring for others, or in situations requiring high concentration. Do not push into practices you are not ready for in order to 'prove the relationship is real.'"
                )
            ),
            ArticleSection(
                heading = "A healthier metaphor",
                paragraphs = listOf(
                    "Think of the body as a 'shared home' or a 'shared instrument,' not a mecha being piloted. The former emphasizes collaboration, respect, and shared belonging; the latter easily leads to a sense of bodily alienation.",
                    "No practice should come at the cost of your sense of reality. Your stability is the foundation that allows this relationship to continue."
                )
            )
        )
    ),
    Article(
        id = "balance-life",
        title = "Balancing Real Life: Do Not Let the Wonderland Live Your Life for You",
        category = "Safety",
        summary = "Neither ignore the relationship nor let it swallow your life.",
        readMinutes = 5,
        evidenceLevel = EvidenceLevel.MIXED,
        sources = listOf(
            SourceCreationGuideEn,
            SourceClinicalEn,
            SourceRedditEn
        ),
        practice = SessionType.MINDFULNESS,
        sections = listOf(
            ArticleSection(
                heading = "The risk of over-investment",
                researchView = true,
                paragraphs = listOf(
                    "Over-investing in the inner space may reduce outside social contact, affect study or work, and even cause identity confusion: difficulty telling which thoughts, emotions, or behaviors come from yourself and which belong to your tulpa.",
                    "Maintaining intense inner interaction for a long time can also lead to psychological burnout. When the mental load is too high, rest is not a betrayal of the relationship."
                )
            ),
            ArticleSection(
                heading = "The risk of under-investment",
                communityView = true,
                paragraphs = listOf(
                    "Too little interaction weakens the emotional bond; your tulpa may feel ignored, and the quality of the relationship may decline. Some tulpas' worldviews need the host's participation to develop, and a long lack of communication may also let the relationship drift.",
                    "This is not a demand that you invest several hours every day, but a reminder that a relationship needs stable, sustainable maintenance."
                )
            ),
            ArticleSection(
                heading = "Three balancing strategies",
                communityView = true,
                paragraphs = listOf(
                    "Time: schedule a period of uninterrupted active time together each day, while using passive forcing to let your tulpa perceive daily life. Adjust flexibly according to real-world arrangements.",
                    "Space: clearly distinguish the wonderland from reality, and give your tulpa an 'anchor' for telling the inner world apart from the outer world. In situations requiring high concentration, agree in advance to pause or reduce interaction.",
                    "Communication: make daily plans together with your tulpa and respect each other's needs and boundaries. When disagreements arise, think from multiple perspectives and try to compromise."
                )
            ),
            ArticleSection(
                heading = "Caring for the host",
                researchView = true,
                paragraphs = listOf(
                    "Prioritize sleep, food, and exercise. Regularly reflect on your psychological state and life satisfaction, and make sure your living environment is positive.",
                    "If unbearable stress, dissociative symptoms, or severe anxiety appear, contact a mental health professional promptly. The more stable the host, the more rooted the relationship."
                )
            )
        )
    )
)
