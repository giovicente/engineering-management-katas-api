-- BEHAVIORAL KATAS
INSERT INTO kata (id, category, description, title, level) VALUES
  (uuid_generate_v4(), 'BEHAVIORAL', 'How do you deliver hard but necessary feedback to a senior team member without damaging confidence or causing defensiveness?', 'Tough Feedback', 'MEDIUM'),
  (uuid_generate_v4(), 'BEHAVIORAL', 'You notice a consistent decline in the enthusiasm and engagement of a high-potential team member. How do you approach it?', 'Dropping Engagement', 'MEDIUM'),
  (uuid_generate_v4(), 'BEHAVIORAL', 'Two developers are frequently in conflict, affecting team productivity. What do you do?', 'Peer Conflict', 'HARD'),
  (uuid_generate_v4(), 'BEHAVIORAL', 'A high-performing developer refuses to accept feedback or collaborate. How do you act without losing this talent?', 'Ego vs Teamwork', 'HARD'),
  (uuid_generate_v4(), 'BEHAVIORAL', 'How do you coordinate a project within a team that has a history of missing deadlines and struggles to connect all parts and people involved, delaying value delivery?', 'Coordinating projects in teams that are not self-manageable', 'HARD'),
  (uuid_generate_v4(), 'BEHAVIORAL', 'A senior analyst on your team has been speaking negatively about the department in informal conversations. This is demotivating other team members. In front of you, they claim everything is fine, but a trusted team member has brought the situation to your attention. How would you address this issue?', 'Senior team member undermining the department behind your back', 'HARD'),
  (uuid_generate_v4(), 'BEHAVIORAL', 'A team member is very good at carrying out their core responsibilities, but they have a distorted view of what it means to be a standout. They believe that simply fulfilling their duties already qualifies as exceptional performance. In your view, what does it take to be considered a top performer, and how would you guide this person to help them truly stand out?', 'Team member with acceptable performance who sees themselves as a top performer', 'MEDIUM'),
  (uuid_generate_v4(), 'BEHAVIORAL', 'A team works in a hybrid model, with 80% on-site and 20% remote. You ask for help in a group chat about a production outage, asking who can take a look. This team has a history of not responding to help requests unless they are extremely direct. Thirty minutes go by and no one responds. How would you address this in the moment, and how would you prevent this from happening again in the future?', 'Ignored requests for help', 'EASY');

-- TECHNICAL KATAS
INSERT INTO kata (id, category, description, title, level) VALUES
  (uuid_generate_v4(), 'TECHNICAL', 'You took over a team with a legacy system full of technical debt. How do you prioritize refactoring without halting deliveries?', 'Inherited Technical Debt', 'MEDIUM'),
  (uuid_generate_v4(), 'TECHNICAL', 'The project has poor test coverage and everyone is afraid of touching the code. How do you lead a turnaround?', 'Low Test Coverage', 'MEDIUM'),
  (uuid_generate_v4(), 'TECHNICAL', 'The deployment pipeline frequently fails, causing instability and delays. How do you address this without paralyzing the team?', 'Fragile Deployment Pipeline', 'EASY'),
  (uuid_generate_v4(), 'TECHNICAL', 'Part of the team wants to adopt a new tech stack while others fear losing productivity. How do you decide?', 'Tech Stack Misalignment', 'MEDIUM'),
  (uuid_generate_v4(), 'TECHNICAL', 'You notice your team is struggling to fully understand the functional and technical scope of what needs to be done. How do you approach this?', 'Team facing comprehension challenges', 'EASY');

-- STRATEGIC KATAS
INSERT INTO kata (id, category, description, title, level) VALUES
  (uuid_generate_v4(), 'STRATEGIC', 'Executive leadership demands deliveries on unrealistic deadlines. How do you communicate technical boundaries without sounding negative?', 'Unrealistic Planning', 'HARD'),
  (uuid_generate_v4(), 'STRATEGIC', 'You realize your team is underutilized due to a lack of challenges. How do you scale delivery without compromising quality?', 'Underused Team Potential', 'EASY'),
  (uuid_generate_v4(), 'STRATEGIC', 'Leadership constantly changes project priorities. How do you protect team focus and morale?', 'Shifting Priorities', 'MEDIUM'),
  (uuid_generate_v4(), 'STRATEGIC', 'As your engineering team scales, how do you balance the pressure for rapid delivery with the growing need for architectural consistency, knowledge sharing, technical debt control, and preserving team autonomy—while still ensuring long-term sustainability, psychological safety, and talent retention in a competitive market?', 'Balancing sustainable growth with competitive deliveries', 'HARD'),
  (uuid_generate_v4(), 'STRATEGIC', 'Stakeholders keep inflating scope during the project. How do you keep it under control?', 'Scope Creep Management', 'MEDIUM');