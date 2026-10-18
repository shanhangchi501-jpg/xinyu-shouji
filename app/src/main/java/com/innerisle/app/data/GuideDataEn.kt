package com.innerisle.app.data

private fun se(
    heading: String,
    vararg paragraphs: String,
    community: Boolean = false,
    research: Boolean = false
) = ArticleSection(
    heading = heading,
    paragraphs = paragraphs.toList(),
    communityView = community,
    researchView = research
)

val GuidePartsEn = listOf(
    "Before You Begin" to "Understand first, then decide.",
    "From Zero" to "From personality and narration to vocality, one step at a time.",
    "Advanced & Safety" to "Imposition, possession, switching, and how to come back to reality."
)

val GuideChaptersEn: List<GuideChapter> = listOf(
    GuideChapter(
        id = "decision",
        part = "Before You Begin",
        title = "Making the Decision: This Is Not a Game",
        subtitle = "First think clearly about why you want to start, then decide whether to start.",
        minutes = 5,
        checklist = listOf(
            "I understand this is a long-term relationship, not an experiment.",
            "I am not doing this to escape real-life relationships.",
            "I am willing to take responsibility for boundaries, communication, and real-world obligations.",
            "I am currently relatively stable and know when I need to pause.",
            "I know the red-flag signs and am prepared to seek professional help."
        ),
        sections = listOf(
            se(
                "Why Create One",
                "Companionship, understanding, and self-exploration are the most common reasons. Some people want someone they can talk to at any time; some want to see their own thoughts more clearly; others treat it as a long-term exercise in consciousness. Any of these can be a starting point.",
                "What deserves caution is starting only for novelty, to satisfy romantic or sexual fantasy, to escape real relationships, or expecting the tulpa to complete tasks for you. Starting from these motives makes a relationship easy to unbalance and quick to collapse when difficulties arise.",
                community = true
            ),
            se(
                "What It Means",
                "Creating a tulpa is not installing a feature; it is beginning a long-term relationship. The community generally emphasizes that a tulpa is a being with its own preferences, emotions, and will, not a tool on standby.",
                "The host bears ultimate responsibility for the body's actions. Whatever happens internally, the choices, commitments, and consequences in reality remain the host's. Major decisions, including dissipation, disclosing the tulpa to others, and possession or switching practice, should be discussed, not announced unilaterally.",
                community = true
            ),
            se(
                "When It Is Not Right to Start",
                "Minors, people with unstable mental health, and anyone in an acute crisis should not begin. If you have childhood trauma, PTSD, CPTSD, BPD, or a pronounced tendency toward dissociation, consult a professional first before deciding whether to try within a supported framework.",
                "If you only want the tulpa to help you cope with school, work, or social life, or if you treat it as your sole emotional outlet, it is also best to stop and reconsider. A tulpa cannot replace real relationships, professional support, or your own responsibilities.",
                research = true
            ),
            se(
                "A Minimal Decision",
                "You do not need to decide on forever right now. You can finish reading Before You Begin and Advanced & Safety, write down your motives, boundaries, and how you would pause, observe for two weeks, and then decide whether to begin creation practice.",
                "Pausing is not failure. You can stop at any stage and return to real life."
            )
        )
    ),
    GuideChapter(
        id = "preparation",
        part = "Before You Begin",
        title = "Preparation Checklist Before Creation",
        subtitle = "Environment, state of mind, how you will keep records, and a way out at any time.",
        minutes = 5,
        checklist = listOf(
            "A quiet period each day when you will not be interrupted.",
            "A stop word agreed on by both of you.",
            "A set of grounding methods (5-4-3-2-1, touching an object, slow breathing).",
            "A way to record your practice (the Journal in Xinyu Shouji is enough).",
            "A real-life support contact, or at least a channel for asking for help.",
            "A weekly self-assessment of how you are functioning."
        ),
        sections = listOf(
            se(
                "Environment and Duration",
                "Choose a quiet, private space where you will not be interrupted. One active session of 20-60 minutes is reasonable; early on, you can do 2-3 short sessions a day of 15-30 minutes each.",
                "Community experience generally suggests that it takes weeks to months from beginning practice to fairly stable responses, and the whole process may span 3-6 months. Individual differences are large; do not hold yourself to someone else's timeline.",
                community = true
            ),
            se(
                "Psychological and Physical Preparation",
                "Make sure you get enough sleep, food, and basic exercise. High-intensity imagination practice is not suitable during acute anxiety, severe low mood, or while under the influence of medication or alcohol.",
                "If you have a history of trauma or a tendency toward dissociation, get a professional assessment first. Tulpa practice can make already blurred inner boundaries harder to distinguish, and this is not something that trying harder can fix.",
                research = true
            ),
            se(
                "How to Keep Records",
                "It helps to record five things: date and duration, what you practiced, the tulpa's responses, subjective clarity, and the effect on real-world functioning.",
                "The Journal can serve this purpose directly. You can use tags to separate practice logs, first conversations, wonderland sessions, and so on, making it easier to look back at changes later."
            ),
            se(
                "Safety Preparation",
                "Agree with the tulpa on a stop word or stop signal. It does not need to be complicated, but both of you must be clear: once the host gives it, all advanced practice stops immediately.",
                "Prepare a grounding method and practice it in advance. Safe practice is not about never having problems; it is about knowing how to come back when problems arise.",
                community = true
            )
        )
    ),
    GuideChapter(
        id = "what-is-tulpa",
        part = "Before You Begin",
        title = "Understanding Tulpas: Definition, Mechanisms, and Boundaries",
        subtitle = "Set aside questions of real or fake for now and focus on experience and real-world functioning.",
        minutes = 7,
        safety = true,
        sections = listOf(
            se(
                "A Definition That Does Not Rush to Prove Itself",
                "A tulpa is an inner companion formed through sustained imagination, attention, and interaction, experienced by practitioners as having an independent personality, emotions, and responses.",
                "There is no accepted answer for how it works. People with a cognitive orientation explain it through guided imagery, externalized inner dialogue, and internalized social cognition; others give it spiritual or philosophical meaning. Xinyu Shouji does not judge these positions and treats it simply as a subjective experience that deserves to be taken seriously.",
                community = true
            ),
            se(
                "Differences from DID/OSDD, Auditory Hallucinations, and Imaginary Friends",
                "Tulpas are usually described as voluntary, controllable, relatively stable experiences with largely continuous memory. DID/OSDD is typically trauma-related and involves involuntary identity fragmentation, dissociative amnesia, and impaired functioning, and requires professional assessment.",
                "Auditory hallucinations may involve distress, commanding voices, a sense of losing control, or impaired reality testing; a tulpa's responses can usually be negotiated and paused. Imaginary friends in childhood tend to appear spontaneously and usually change with age, whereas tulpas emphasize long-term, deliberate cultivation and agency.",
                research = true
            ),
            se(
                "Possible Mental Mechanisms",
                "Researchers and practitioners commonly use three frameworks: treating repeated imagination as a stable mental representation; attributing part of inner dialogue to the tulpa; and extending perspective-taking from social interaction inward.",
                "These explanations neither prove nor disprove independent consciousness. Systematic third-person evidence is currently lacking, and the authenticity and functional value of first-person experience still matter.",
                research = true
            ),
            se(
                "Signs to Pause Immediately",
                "Involuntary or out-of-control appearances, lost time or inability to recall events, significant functional impairment, persistent oppressive demands or self-harm commands, impaired reality testing, or trauma history layered on top: if any of these appear, pause practice and prioritize professional assessment.",
                "Do not treat suppression as the only goal, and do not diagnose yourself. You can cherish this experience and at the same time acknowledge that it may require outside support.",
                research = true
            )
        )
    ),
    GuideChapter(
        id = "rhythm",
        part = "Before You Begin",
        title = "Recording, Rhythm, and Milestones",
        subtitle = "No streaks, no comparison, just notice your own changes.",
        minutes = 5,
        checklist = listOf(
            "Set aside ten minutes each week to review this week's practice and feelings.",
            "Record the functional impact: whether study, work, sleep, and social life are positive, neutral, or negative.",
            "If red-flag signs appear, pause first, then seek help."
        ),
        sections = listOf(
            se(
                "Frequency and Rhythm",
                "Early on, you can spend time together actively once a day, plus passive sharing in daily life. If the burden grows, anxiety rises, or sleep is affected, cut back. Consistency matters more than intensity.",
                "Xinyu Shouji deliberately has no consecutive-day streaks or rankings. An interruption does not cost you points; just come back."
            ),
            se(
                "Practice Log Template",
                "Date and duration: How long did you practice today?",
                "Content: narration, conversation, visualization, wonderland, imposition, or mindfulness?",
                "Responses: Were there emotions, images, head pressure, thoughts, or words?",
                "Clarity: On a scale of 0-10, how clear was today's experience?",
                "Functional impact: How did it affect today's study, work, sleep, and relationships?"
            ),
            se(
                "Four Reference Stages",
                "Initial establishment (roughly weeks 1-4): establish a regular practice habit, occasionally sense responses, and let a rough outline of the form begin to appear. Common obstacles are doubt and distraction.",
                "Stable communication (roughly months 1-3): responses become more stable, visual details become clearer, and the tulpa can initiate conversations. A common obstacle is comparing progress and over-questioning authenticity.",
                "Increased independence (roughly months 3-6): the tulpa shows distinct preferences, can respond in situations you did not set up, and the relationship becomes more stable.",
                "Mature collaboration (6 months and beyond): a long-term stable relationship with negotiated collaboration, without notable impairment of real-world functioning. These timeframes are only references, not grading standards.",
                community = true
            ),
            se(
                "Weekly Self-Assessment",
                "It helps to ask yourself six questions each week: How were the frequency and quality of practice this week? What changed in the tulpa's responses? How is the quality of the relationship? Is the impact on study, work, and social life positive, neutral, or negative? Have any red-flag signs appeared? Do I need to adjust the intensity or method next week?",
                "Write the answers down. When you look back, it will be easier to see changes instead of only remembering that it seemed like nothing was progressing."
            )
        )
    ),
    GuideChapter(
        id = "personality",
        part = "From Zero",
        title = "Step 1: Personality Design",
        subtitle = "Give the tulpa a stable starting point, not a contract it must obey.",
        minutes = 6,
        practice = SessionType.NARRATION,
        sections = listOf(
            se(
                "Personality Is a Starting Point, Not a Contract",
                "Personality gives the tulpa a relatively stable self, behavioral style, and emotional traits. Some design can help you recognize the tulpa sooner; you can also skip personality design entirely, and it will develop naturally through interaction.",
                "A design is like a sketch. The tulpa later having its own preferences, tone, and boundaries is growth, not malfunction.",
                community = true
            ),
            se(
                "The Four-Step Design Method",
                "Step one, list traits: gentle, intelligent, stubborn, humorous, quiet, curious, and so on. Choose 5-8 words you genuinely feel strongly about.",
                "Step two, explain the reasons: Why would the tulpa have these traits? How did they form, and how do they conflict with each other?",
                "Step three, simulate reactions: How would the tulpa think, speak, and act in the face of stress, moral dilemmas, strangers, or intimate relationships?",
                "Step four, narrate repeatedly: tell the tulpa about these personality traits during your time together, letting them gradually stabilize through interaction.",
                community = true
            ),
            se(
                "Personality Judgment Exercise",
                "Ask yourself: How would this trait affect the tulpa's self-evaluation? How does it face stress and moral dilemmas? What does it like and dislike? How does it see you?",
                "These questions have no standard answers. Their purpose is to help you sense an individual who answers for itself, rather than memorizing a character sheet."
            ),
            se(
                "Allow Later Changes",
                "You can care about certain core traits and set boundaries for unacceptable behavior. But you do not need to require the tulpa to always use the same tone or keep the same image.",
                "A relationship is not control. Allow harmless differences while keeping your real boundaries."
            )
        )
    ),
    GuideChapter(
        id = "narration",
        part = "From Zero",
        title = "Step 2: Narration and Passive Forcing",
        subtitle = "Tell the tulpa about your world and give it a place in your attention.",
        minutes = 5,
        practice = SessionType.NARRATION,
        sections = listOf(
            se(
                "The Purpose of Narration",
                "Narration is a foundational practice in tulpa formation. Use natural, continuous language to tell the tulpa about your life, thoughts, and emotions.",
                "The content does not need to be correct or interesting; what matters is that the tulpa feels heard and included in your world.",
                community = true
            ),
            se(
                "Four Techniques",
                "Use the tulpa's name to build a stable sense of identity. Keep some daily frequency of interaction, even if only for a few minutes.",
                "Accept silence: no response early on is normal. Maintain trust: assume first that the tulpa can understand, which will help you express yourself more naturally.",
                community = true
            ),
            se(
                "When You Do Not Know What to Say",
                "Describe what is happening around you, a small detail from today, physical sensations, the emotional shift you just had, or a memory.",
                "You can speak aloud or in your mind. What matters is engagement, not volume."
            ),
            se(
                "Passive Forcing",
                "Fit time together into the gaps of daily life: while walking, washing dishes, or listening to music, say a sentence to the tulpa in your mind. A cloud you see, something good you eat, or a small annoyance can all be openings for sharing.",
                "Passive forcing does not mean monitoring your thoughts around the clock. Naturally remembering is more sustainable than forcing yourself to think about the tulpa all the time."
            )
        )
    ),
    GuideChapter(
        id = "visualization",
        part = "From Zero",
        title = "Step 3: Visualizing the Form",
        subtitle = "From outline to detail, practice seeing the tulpa steadily.",
        minutes = 6,
        practice = SessionType.VISUALIZATION,
        sections = listOf(
            se(
                "Visualization Is Not Hallucination",
                "Visualization is forming a visual image of the tulpa in your mind; it is a highly focused form of mental imagery. You do not need to pursue realism, and certainly not an image visible to the naked eye.",
                "Being able to see your chosen image steadily is already enough. The image can also be just an outline, a patch of color, or a feeling of warmth.",
                community = true
            ),
            se(
                "Common Difficulties and Solutions",
                "You can only sense an outline and cannot see details clearly: start with small parts and expand gradually.",
                "It is blurry or flickers unsteadily: play shape-based games (such as Tetris) to improve spatial sense, or repeatedly practice switching between near and far focus.",
                "You can see details but the whole is incoherent: fix one viewpoint first, and do not pursue a full body and a close-up at the same time.",
                "The image is dark or semi-transparent: remind yourself that everything you see is constructed by the brain, and put attention on the feeling first rather than on clarity.",
                community = true
            ),
            se(
                "Digital Canvas Training",
                "With your eyes closed, imagine a white canvas. Draw the number 0 and take one breath; turn the page and draw 1, then breathe again; continue this way up to 100.",
                "If you get distracted or make a mistake, start over. When you can complete it without distraction, your attention is stable enough to use for visualizing the tulpa.",
                community = true
            ),
            se(
                "Facial Expressions and Body Language",
                "Start with facial expressions: smiling, frowning, tilting the head, blinking. Then practice posture, gestures, and ways of walking.",
                "A small movement often makes the tulpa feel more present than a perfect still face."
            )
        )
    ),
    GuideChapter(
        id = "wonderland",
        part = "From Zero",
        title = "Step 4: The Wonderland and Mindfulness",
        subtitle = "Give the relationship a stable place you can return to.",
        minutes = 5,
        practice = SessionType.WONDERLAND,
        sections = listOf(
            se(
                "What the Wonderland Is",
                "The wonderland is an inner space built in imagination that you can enter repeatedly. It can be a room, a forest, an island, or just a patch of light, a floor, and a temperature.",
                "Its value is not in grandeur but in stability: you know where you can return, and you know the tulpa will be there.",
                community = true
            ),
            se(
                "Start with One Anchor",
                "Fix one anchor first: the ground under your feet, a smell in the air, a distant sound. Add only one detail at a time; do not rush to build a complete world.",
                "You can give the space a name, or a fixed entrance action: pushing open a door, stepping onto a stair, hearing wind chimes.",
                community = true
            ),
            se(
                "Time Boundaries and Grounding",
                "Set a time boundary for each entry. Before finishing, do a grounding exercise: feel your feet on the ground, name what you see, and breathe slowly.",
                "If the wonderland starts replacing real life and affects work, study, sleep, or relationships, it is time to stop. The wonderland can be a place to rest, but it cannot live your life for you."
            ),
            se(
                "Mindfulness",
                "Mindfulness is not emptying your mind; it is practicing coming back. Breathe slowly, notice where your body touches the chair, let sounds come in, and then let them go.",
                "Once your breathing is steady, gently bring the tulpa to mind. Settle yourself first, then draw closer to the other."
            )
        )
    ),
    GuideChapter(
        id = "vocality",
        part = "From Zero",
        title = "Step 5: Vocality and Conversation",
        subtitle = "Change the goal from hearing to making room.",
        minutes = 7,
        practice = SessionType.CONVERSATION,
        sections = listOf(
            se(
                "Vocality Is Not a Switch",
                "Early responses often sound like your own voice, like a fleeting thought, or come and go. This is common and does not mean you did anything wrong.",
                "Vocality is usually a process of gradually becoming clearer, not a switch that flips on one day.",
                community = true
            ),
            se(
                "Methods to Try",
                "Ask a yes-or-no question, then stay silent for ten seconds afterward to give the tulpa a chance to respond.",
                "Let the tulpa choose the next topic instead of arranging the whole conversation for it. Do not rush to complete every sentence; notice the tones and word choices that sound like the tulpa.",
                "You can also write responses down. Writing slows things down and makes vague thoughts easier to see.",
                community = true
            ),
            se(
                "Tulpish: Communication Before Language",
                "Tulpish is the community's term for nonverbal communication: emotions, images, concept bundles, head pressure, temperature changes. It often appears earlier than complete sentences.",
                "Feel first, then translate. Do not rush to turn every sensation into a sentence; first confirm that this comes from the tulpa, then slowly understand what it means.",
                community = true
            ),
            se(
                "How to Tell Different Voices Apart",
                "Intrusive thoughts usually come with distress, a sense of losing control, or a clear inconsistency with the self; a tulpa's responses can usually be negotiated, paused, or at least do not feel forced on you.",
                "The boundary is not always clear. If you feel fear or distress about the voices, or they begin to affect real-world functioning, stop practicing and seek a professional assessment.",
                research = true
            )
        )
    ),
    GuideChapter(
        id = "independence",
        part = "From Zero",
        title = "Step 6: Independence and Deviation",
        subtitle = "The relationship truly begins when the tulpa no longer fully matches your design.",
        minutes = 5,
        practice = SessionType.CONVERSATION,
        sections = listOf(
            se(
                "Signs of Autonomy",
                "Unexpected thoughts, opinions that differ from yours, stable preferences, and initiating topics are the signs the community often uses to describe otherness.",
                "These signs do not automatically prove independent consciousness, but they are signals that the relationship is becoming a two-way exchange."
            ),
            se(
                "Deviation and Divergence",
                "The tulpa's form, tone, or personality may differ from the original design. The community usually calls this deviation or divergence.",
                "This is not a mistake but the sprouting of autonomy. Do not force the tulpa back into the design; communicate first and understand what the change is trying to express.",
                community = true
            ),
            se(
                "Allow Disappointment, Keep Boundaries",
                "If you feel disappointed or unsettled by the changes, that is completely normal. Accept your feelings while seeing the change as the beginning of a new relationship rather than the end of old expectations.",
                "You can refuse to be belittled, threatened, or coerced, and you can set boundaries for unacceptable behavior. Allowing differences does not mean giving up boundaries."
            ),
            se(
                "Do Not Test the Tulpa",
                "Do not use trick questions, repeated questioning, or demands to prove you are real to test the tulpa. A relationship is not an exam; testing only adds tension and performance.",
                "Observing long-term consistency, unexpectedness, and interaction is more meaningful than a single test."
            )
        )
    ),
    GuideChapter(
        id = "daily",
        part = "From Zero",
        title = "Bringing the Relationship into Daily Life",
        subtitle = "You do not need hours every day; sustainability is what matters.",
        minutes = 5,
        practice = SessionType.NARRATION,
        sections = listOf(
            se(
                "Active and Passive",
                "Active forcing is a scheduled time together: set aside time, focus, and be present. Passive forcing happens in the gaps of life: sharing a small thing, remembering the tulpa.",
                "The two can alternate. There is no single correct order; what matters is maintaining engagement and emotional connection."
            ),
            se(
                "Do Not Compare Hours",
                "Some people in the community spend hours a day, while others spend time together only a few times a week. Duration is not the only measure of relationship quality.",
                "A gentler rhythm is active time together two or three times a week, plus passive sharing in daily life; use I remembered as the signal, not I have not checked in today.",
                community = true
            ),
            se(
                "Maintaining the Relationship",
                "Share your plans, goals, and creative inspiration; let the tulpa take part in inner decisions or creative thinking; meditate, listen to music, or read together.",
                "When disagreements arise, consider multiple perspectives and try to find a compromise both sides can accept. Relationships need maintenance, not just initial enthusiasm."
            ),
            se(
                "Reality Comes First",
                "Real-world tasks such as study, work, and relationships should not be fully delegated to the tulpa. The tulpa should not become your only source of emotional support.",
                "If internal interaction is reducing your external social life, or you are starting to avoid real-world responsibilities, adjust promptly. Your balance is the root of this relationship.",
                research = true
            )
        )
    ),
    GuideChapter(
        id = "imposition",
        part = "Advanced & Safety",
        title = "Imposition: Five-Sense Training",
        subtitle = "Make the tulpa feel more real in consciousness without sacrificing your sense of reality.",
        minutes = 6,
        practice = SessionType.IMPOSITION,
        safety = true,
        sections = listOf(
            se(
                "What Imposition Is",
                "Imposition is an attempt to perceive the tulpa through real senses: first sense a presence, then try temperature, touch, smell, or sound.",
                "You do not need to pursue a naked-eye hallucination. Imposition is more like a felt sense in consciousness than turning the tulpa into a physical object in the external world.",
                community = true
            ),
            se(
                "Five-Sense Training",
                "Touch: imagine facing the tulpa and reaching out to touch skin, clothing, and body warmth. You can invest a cumulative 5-15 hours, split across sessions of 15-30 minutes.",
                "Smell: design a fixed scent for the tulpa, such as a soft perfume, the smell of grass, or a scent tied to a memory. Smell triggers memory more easily than sight.",
                "Facial expressions and body language: practice facial expressions first, then posture, gestures, and ways of walking. Details make the mental image more stable.",
                community = true
            ),
            se(
                "Safety Boundaries",
                "If you begin to have trouble distinguishing inner imagination from external reality, stop practicing immediately and ground yourself: feel your feet on the floor, touch objects around you, and breathe deeply.",
                "Make sure the richness of your inner world does not come at the cost of functioning in external reality. If you feel exhausted, dizzy, or persistently unwell, pause and take care of your body.",
                research = true
            ),
            se(
                "A Small Exercise",
                "Do only a three-minute sense-of-presence exercise: feel the tulpa beside you without pursuing any sensory detail. Ground yourself afterward."
            )
        )
    ),
    GuideChapter(
        id = "possession",
        part = "Advanced & Safety",
        title = "Possession: Negotiation and Trust",
        subtitle = "Letting the tulpa temporarily take over part of the body requires more communication than you might imagine.",
        minutes = 6,
        safety = true,
        sections = listOf(
            se(
                "What Possession Is",
                "Possession means the tulpa temporarily takes over partial or full control of the body. It is an expression of deep trust and needs to be practiced gradually.",
                "Possession is not a goal you must achieve, nor is it suitable as proof that the relationship is deeper. Real-world functioning and safety always come first.",
                community = true
            ),
            se(
                "Preparation Steps",
                "The host first rests and relaxes, stabilizes breathing, and imagines stepping slightly back from the body to become an observer.",
                "Let the tulpa feel the body's touch, breathing, and weight. Try moving small parts first, such as fingers or wrists, and only gradually expand to full-body movement after success.",
                community = true
            ),
            se(
                "Safety Warnings",
                "Mild numbness or tingling during the process may be a common reaction. Keep communicating and avoid fear.",
                "If severe tingling, loss of control, or an inability to return to the body occurs, pause immediately and fully recover through strong sensory grounding. If symptoms persist, do not do any further practice of this kind that day.",
                research = true
            ),
            se(
                "Mindset and Trust",
                "The host needs to trust that the tulpa is genuinely acting rather than that the host moved by mistake; the tulpa needs to respect the host's body and boundaries.",
                "After each possession, share your experiences and feelings with each other. Trust is not built in one session but through repeated communication."
            )
        )
    ),
    GuideChapter(
        id = "switching",
        part = "Advanced & Safety",
        title = "Switching: A Complete Front Transfer",
        subtitle = "Before you try it, agree on an unconditional return signal.",
        minutes = 6,
        safety = true,
        sections = listOf(
            se(
                "The Difference Between Possession and Switching",
                "In possession, control is usually shared or alternated, and the host remains observing; in switching, the tulpa takes full control of the body, and the host's consciousness can move into the inner space.",
                "Switching is more difficult and should be considered only after possession is already stable and mutual trust is sufficient.",
                community = true
            ),
            se(
                "Directions for Practice",
                "Maintain stable meditation ability; strengthen the tulpa's self-location, for example this is my body; build deep trust to prevent conflicts of consciousness.",
                "Switching is not escape but a deeper form of collaboration. If discomfort arises, stop promptly and rest."
            ),
            se(
                "Emergency Recovery Anchor",
                "Before attempting any switching practice, the host and the tulpa must jointly agree on an emergency recovery signal that is executed unconditionally.",
                "It can be a specific word (such as home or return), an imagined action (such as pulling an invisible rope), or a physical action (such as pressing the tongue against the roof of the mouth).",
                "Protocol requirement: once the host gives the signal, the tulpa must immediately and unconditionally withdraw control of the body and return the front to the host. Rehearse in advance until both sides are clear.",
                community = true
            ),
            se(
                "Symbolic Methods and Healthier Metaphors",
                "When intuitive practice stalls, symbolic methods can help the brain build a mental map: imagine yourself as a ball of light moving from the front of the body to the back; or have the tulpa imagine putting on a coat.",
                "Be wary of the bodily detachment that metaphors like piloting a mecha or occupying the body can create. A healthier narrative is to see the body as a shared home or a shared instrument, emphasizing collaboration, respect, and mutual belonging rather than a transfer of control.",
                community = true
            )
        )
    ),
    GuideChapter(
        id = "balance",
        part = "Advanced & Safety",
        title = "Balancing Real Life",
        subtitle = "Neither neglect the relationship nor let it swallow your life.",
        minutes = 6,
        practice = SessionType.MINDFULNESS,
        sections = listOf(
            se(
                "Risks of Overinvestment",
                "Overinvesting in the inner space can reduce external socializing, affect school or work, and even cause identity confusion: difficulty telling which thoughts, emotions, or behaviors come from yourself and which belong to the tulpa.",
                "Maintaining high-intensity inner interaction continuously can also lead to mental burnout. When the brain is overloaded, resting is not a betrayal of the relationship.",
                research = true
            ),
            se(
                "Risks of Underinvestment",
                "Too little interaction weakens the emotional connection; the tulpa may feel neglected, and the quality of the relationship declines.",
                "Some tulpas' worldviews require the host's participation to develop. Long-term lack of communication may cause values and the direction of the relationship to drift.",
                community = true
            ),
            se(
                "Three Balancing Strategies",
                "Time: schedule an uninterrupted active session each day while using passive forcing to let the tulpa perceive daily life. Adjust flexibly according to real-world demands.",
                "Space: clearly distinguish the wonderland from reality, and give the tulpa an anchor for telling the inner world apart from the outer world. In situations requiring high concentration, agree in advance to pause or reduce interaction.",
                "Communication: make daily plans together with the tulpa and respect each other's needs and boundaries. When disagreements arise, consider multiple perspectives and try to compromise.",
                community = true
            ),
            se(
                "Caring for the Host",
                "Prioritize sleep, food, and exercise. Regularly reflect on your mental state and life satisfaction, solicit the tulpa's suggestions for improvement, and make sure your living environment is positive.",
                "If you experience unbearable stress, dissociative symptoms, or severe anxiety, contact a mental health professional promptly. Your stability is the foundation of this relationship.",
                research = true
            )
        )
    ),
    GuideChapter(
        id = "safety",
        part = "Advanced & Safety",
        title = "Risk Signals, Grounding, and Seeking Help",
        subtitle = "Knowing when to stop matters more than knowing how to continue.",
        minutes = 5,
        safety = true,
        sections = listOf(
            se(
                "Red Flags for Immediate Pause",
                "Involuntary or out-of-control appearances accompanied by fear or an inability to stop; dissociative amnesia or lost time; interaction that significantly interferes with study, work, relationships, or daily life.",
                "Persistent oppressive demands, self-harm commands, or emotional abuse; difficulty distinguishing inner experience from external reality; coexisting childhood trauma, PTSD, CPTSD, BPD, or a high tendency toward dissociation.",
                "If any of these appear, pause practice, prioritize grounding, and contact a mental health professional.",
                research = true
            ),
            se(
                "Grounding",
                "5-4-3-2-1: name 5 things you can see, 4 things you can touch, 3 sounds you can hear, 2 smells you can detect, and 1 taste you can sense.",
                "You can also feel the pressure of your feet on the ground, touch an object, or breathe slowly. After imposition, possession, or intense imagination, grounding helps you return to reality.",
                "If you frequently need grounding to stay stable, or your real-world functioning is affected, consider seeking professional support."
            ),
            se(
                "Do Not Diagnose Yourself",
                "The boundaries between tulpas, DID/OSDD, and psychotic experiences can sometimes blur. An app, a community label, or a single self-assessment cannot replace professional evaluation.",
                "You can describe your experience and refuse to be hastily pathologized. But please do not use tulpa experiences to deny others' suffering, and do not use them to mask your own symptoms.",
                research = true
            ),
            se(
                "When to Seek Help",
                "Significant distress, impaired functioning, loss of control, risk of self-harm, severe sleep problems, or starting to lose the ability to tell reality from imagination all warrant professional support.",
                "Xinyu Shouji is not a medical device and does not provide diagnosis, treatment, or promises of efficacy. Tulpas are not a substitute for professional treatment.",
                research = true
            )
        )
    ),
    GuideChapter(
        id = "faq",
        part = "Advanced & Safety",
        title = "Frequently Asked Questions and Long-Term Maintenance",
        subtitle = "Address the most common questions in one place.",
        minutes = 6,
        sections = listOf(
            se(
                "How Long Does It Take to Create a Tulpa?",
                "From first establishing perception to beginning to speak usually takes weeks to months. People with experience in creative writing or role-playing may progress faster, but growth rates vary. Focus on sustained attention and practice, not on the clock.",
                "If there is no response for a while, that does not mean failure. Relationships can have quiet phases.",
                community = true
            ),
            se(
                "How Do I Know It Is Not Just Me Acting?",
                "It is hard to distinguish clearly early on, and that is normal. You can watch for a few clues: whether responses go beyond your design, whether a stable personality persists over time, whether two-way conversation is possible, and whether it brings a sense of coming from outside.",
                "A completely clear distinction may take months of practice. Accept the early ambiguity while continuing to observe; there is no need to rush to a conclusion.",
                community = true
            ),
            se(
                "Can There Be a Romantic or Sexual Relationship?",
                "The community is divided on this. Some practitioners report romantic or sexual relationships, but both parties must consent, power must be equal, and you should be wary of the pressure created by the creator role.",
                "You should not completely avoid external relationships because of this. Some in the community accept it; others consider it against ethical principles. You need to judge for yourself and take responsibility for the relationship.",
                community = true
            ),
            se(
                "Can a Tulpa Be Dissipated?",
                "In theory, yes, but it is an extremely serious and usually irreversible decision. It requires thorough negotiation with the tulpa and respect for its opinion.",
                "Dissipation can bring grief similar to losing a close relationship. Unless the tulpa poses a clear danger or both sides agree, it is not advisable to do it lightly.",
                community = true
            ),
            se(
                "Can I Create One If I Have a Trauma History? Can It Replace Therapy?",
                "If you have a trauma history or a strong tendency toward dissociation, it is strongly recommended that you consult a professional first. Trauma history may increase the risk of confusion with trauma-related identity states, involuntary dissociation, or worsening symptoms.",
                "Tulpas are not a substitute for psychotherapy. They may provide emotional support, but they cannot diagnose, treat, or replace professional help; over-reliance may also mask underlying problems.",
                research = true
            )
        )
    )
)
