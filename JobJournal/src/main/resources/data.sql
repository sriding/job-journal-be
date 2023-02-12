/*User 1 will be used for testing purposes*/
INSERT INTO users (_user_id, _auth0_id, _deactivate, _user_creation_date, _user_update_date)
SELECT 1, "google-oauth2|110428753866664923333", false, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _user_id FROM users where _auth0_id = "google-oauth2|110428753866664923333");
INSERT INTO userprofiles (_profile_id, _profile_name, _user_id_fk_profile, _profile_creation_date, _profile_update_date) 
SELECT 1, "Stephen Riding", 1, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _profile_id FROM userprofiles WHERE _profile_name = "Stephen Riding");
INSERT INTO setting (_setting_id, _user_id_fk_setting, _setting_creation_date, _setting_update_date)
SELECT 1,1, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _setting_id FROM setting WHERE _user_id_fk_setting = 1);
INSERT INTO post (_post_id, _post_notes, _user_id_fk_post, _post_creation_date, _post_update_date)
SELECT 1, "testing post notes", 1, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _post_id FROM post WHERE _post_notes = "testing post notes");
INSERT INTO company (_company_id, _company_name, _company_information, _company_website, _post_id_fk_company, _company_creation_date, _company_update_date)
SELECT 1, "testing company name", "", "", 1, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _company_id FROM company WHERE _company_name = "testing company name");
INSERT INTO job (_job_id, _job_title, _job_type, _job_information, _job_location, _job_status, _job_application_submitted_date, _job_application_dismissed_date, _post_id_fk_job, _job_creation_date, _job_update_date)
SELECT 1, "testing job title", "", "", "", "", "", "", 1, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _job_id FROM job WHERE _job_title = "testing job title");
/*
    User 2 will be used to display sample data for the client to play with.
    Multiple post entries will need to be entered for user 2
*/
INSERT INTO users (_user_id, _auth0_id, _deactivate, _user_creation_date, _user_update_date)
SELECT 2, "auth0|638e7fc19cb3608cdc7f5723", false, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _user_id FROM users where _user_id = 2);
INSERT INTO userprofiles (_profile_id, _profile_name, _user_id_fk_profile, _profile_creation_date, _profile_update_date) 
SELECT 2, "Bot Account", 2, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _profile_id FROM userprofiles WHERE _profile_id = 2);
INSERT INTO setting (_setting_id, _user_id_fk_setting, _setting_creation_date, _setting_update_date)
SELECT 2,2, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _setting_id FROM setting WHERE _setting_id = 2); 
/**/
INSERT INTO post (_post_id, _post_notes, _user_id_fk_post, _post_creation_date, _post_update_date)
SELECT 2, "There are no notes to write.", 2, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _post_id FROM post WHERE _post_id = 2);
INSERT INTO company (_company_id, _company_name, _company_information, _company_website, _post_id_fk_company, _company_creation_date, _company_update_date)
SELECT 2, "BlueSky", "BlueSky is a biotechnology startup pioneering programmable molecular technologies for multiplexed quantitative bioimaging. 
We are looking for talented people to help deliver state-of-the-art reagents to our customers and build a solid foundation of the company for sustainable growth.", 
"www.google.com", 2, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _company_id FROM company WHERE _company_id = 2);
INSERT INTO job (_job_id, _job_title, _job_type, _job_information, _job_location, _job_status, _job_application_submitted_date, _job_application_dismissed_date, _post_id_fk_job, _job_creation_date, _job_update_date)
SELECT 2, "Molecular Manufacturing and Operations Associate", "JUNIOR", "Perform a variety of hands-on tasks and use laboratory tools and skills to synthesize oligonucleotides, reagents, and other biomolecules.
Conduct quality control of molecular kits, interpret and respond to validation data, and ensure production outputs meet all specifications of high quality reagents.", 
"Chicago, MI", "APPLIED", "2020-12-12", "2020-12-12", 2, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _job_id FROM job WHERE _job_id = 2);
/**/
INSERT INTO post (_post_id, _post_notes, _user_id_fk_post, _post_creation_date, _post_update_date)
SELECT 3, "Need Cold Fusion knowledge.", 2, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _post_id FROM post WHERE _post_id = 3);
INSERT INTO company (_company_id, _company_name, _company_information, _company_website, _post_id_fk_company, _company_creation_date, _company_update_date)
SELECT 3, "FFusion", "FFusion is an IT Solutions company focusing on custom web-based application development, mobile application development, and IT consulting. FFusion works with clients to learn their business and wrap new, custom technology around the client’s processes. FFusion creates mobile, web applications that feed information as it happens, and eliminates the need for stacks of paperwork and daily data entry", 
"www.google.com", 3, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _company_id FROM company WHERE _company_id = 3);
INSERT INTO job (_job_id, _job_title, _job_type, _job_information, _job_location, _job_status, _job_application_submitted_date, _job_application_dismissed_date, _post_id_fk_job, _job_creation_date, _job_update_date)
SELECT 3, "Software Developer", "MID", "We are looking for a software developer with knowledge of Microsoft technologies, Java, JavaScript, .Net and Cold Fusion to support new and existing applications on both iOS and android platforms. Experience with SQL database and Adobe Air a plus. We require strong dedication and team work to be successful. Only candidates with a proven track-record of team work and “can do” attitude will be considered.", 
"Brea, CA", "SAVED", "2020-12-12", "2020-12-12", 3, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _job_id FROM job WHERE _job_id = 3);
/**/
INSERT INTO post (_post_id, _post_notes, _user_id_fk_post, _post_creation_date, _post_update_date)
SELECT 4, "N/A", 2, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _post_id FROM post WHERE _post_id = 4);
INSERT INTO company (_company_id, _company_name, _company_information, _company_website, _post_id_fk_company, _company_creation_date, _company_update_date)
SELECT 4, "Iler", "Iler is a rapidly growing eCommerce company selling on Amazon (www.amazon.com/reli), Walmart, Shopify (ShopReli.com), and eBay seeing 100% year over year growth. We currently service 5,000+ orders daily across our eCommerce marketplaces, with over 1,000,000+ unique customers such as Marvel, Dominos, and Lululemon.", 
"www.google.com", 4, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _company_id FROM company WHERE _company_id = 4);
INSERT INTO job (_job_id, _job_title, _job_type, _job_information, _job_location, _job_status, _job_application_submitted_date, _job_application_dismissed_date, _post_id_fk_job, _job_creation_date, _job_update_date)
SELECT 4, "Junior Software Developer (Python)", "JUNIOR", "Using Python to develop back-end scripts to automate and scale processes associated with digital advertising, product launches, supply chain operations, and other eCommerce areas
Understanding of the use of 'Pandas' Python Library
Analyzing datasets related to advertising, eCommerce products, and supply chain in order to extract insights and actionable next steps
Collaborating with digital advertising, product, and supply chain teams to identify and pursue opportunities for optimization
Scoping out and building scripts based on specified inputs and desired outputs
Devising strategies to support marketing and advertising initiatives for driving growth / customer acquisition", 
"Remote", "APPLIED", "2020-12-12", "2020-12-12", 4, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _job_id FROM job WHERE _job_id = 4);
/**/
INSERT INTO post (_post_id, _post_notes, _user_id_fk_post, _post_creation_date, _post_update_date)
SELECT 5, "N/A", 2, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _post_id FROM post WHERE _post_id = 5);
INSERT INTO company (_company_id, _company_name, _company_information, _company_website, _post_id_fk_company, _company_creation_date, _company_update_date)
SELECT 5, "ChemBox", "ChemBox are engaged in the discovery and development of cell-based therapeutics and tools that aid in the treatment of human degenerative disorders. Through responsible research and development, we strive to be innovative leaders in biotechnology and regenerative medicine, renowned worldwide for our scientific and medical achievements and contributions to the health and well-being of communities. We have two business units, ChemBox, which is focused on research & development within our platforms of cell-based therapeutics and derivatives of such, and ChemBox, our clinical arm which is seeking to advance our patented cell-based technologies for potential therapeutic applications.", 
"www.google.com", 5, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _company_id FROM company WHERE _company_id = 5);
INSERT INTO job (_job_id, _job_title, _job_type, _job_information, _job_location, _job_status, _job_application_submitted_date, _job_application_dismissed_date, _post_id_fk_job, _job_creation_date, _job_update_date)
SELECT 5, "LABORATORY ASSISTANT/SHIPPING & RECEIVING CLERK", "ASSOCIATE", "· Assist scientists and support in research needs

· Stock and maintain all areas of the laboratory and cleanroom

· Clean and maintain all laboratory equipment

· Properly maintain laboratory logs, including temperature, cleaning, and maintenance logs

· Understand and demonstrate the proper aseptic gowning procedure for cleanroom

· Ensure good laboratory practices and quality standards are being upheld

· Ship and receive all goods to and from the facility

· Communicate with shipping providers worldwide

· Maintain shipping and receiving logs for all products

· Ensure material and supplier qualifications are met", 
"Tampa Bay, FL", "APPLIED", "2020-12-12", "2020-12-12", 5, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _job_id FROM job WHERE _job_id = 5);
/**/
INSERT INTO post (_post_id, _post_notes, _user_id_fk_post, _post_creation_date, _post_update_date)
SELECT 6, "Knowledge of Health Insurance Industry - Claims processing, 835,837 testingPBM, ICD 9/10, Medicare, or Medicaid.", 2, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _post_id FROM post WHERE _post_id = 6);
INSERT INTO company (_company_id, _company_name, _company_information, _company_website, _post_id_fk_company, _company_creation_date, _company_update_date)
SELECT 6, "Consulting Company", "N/A", "www.google.com", 6, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _company_id FROM company WHERE _company_id = 6);
INSERT INTO job (_job_id, _job_title, _job_type, _job_information, _job_location, _job_status, _job_application_submitted_date, _job_application_dismissed_date, _post_id_fk_job, _job_creation_date, _job_update_date)
SELECT 6, "Medicaid Medicare claims MMIS EDI 834 837 healthcare software Testing", "MID", "Ability to design, develop, test and debug tests cases/Scripts/plans/strategy documents
Minimum four (4) years’ experience working on project(s) involving the implementation of software development life cycle(s) (SDLC).
Preparation of Defect Status Reports and Daily/Weekly Status Reports.
Document and interpret business and technical requirements for testing utilizing various development methodologies (e.g. Agile, Scrum, Iterative, Waterfall, etc.)
Extensive experience in Test Management tools building Test Plans and Test Cases using tools like JIRA, Xray and Azure DevOps (VSTS)
Hands on experience in Excel and Status Reports.
Effective communication skills to articulate testing result data to multiple audiences.
Command center Experience
Strong and effective inter-personal and communication skills and the ability to interact professionally with a diverse group of clients and team members", 
"Remote", "SAVED", "2020-12-12", "2020-12-12", 6, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _job_id FROM job WHERE _job_id = 6);
/**/
INSERT INTO post (_post_id, _post_notes, _user_id_fk_post, _post_creation_date, _post_update_date)
SELECT 7, "N/A", 2, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _post_id FROM post WHERE _post_id = 7);
INSERT INTO company (_company_id, _company_name, _company_information, _company_website, _post_id_fk_company, _company_creation_date, _company_update_date)
SELECT 7, "Zeonmetrix", "Zeonmetrix is a fast-growing small business headquartered in Arlington, VA. We are dedicated to providing federal agencies with legendary customer service and focused solutions for their business and technology needs.", "www.google.com", 7, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _company_id FROM company WHERE _company_id = 7);
INSERT INTO job (_job_id, _job_title, _job_type, _job_information, _job_location, _job_status, _job_application_submitted_date, _job_application_dismissed_date, _post_id_fk_job, _job_creation_date, _job_update_date)
SELECT 7, "Quality Assurance Analyst", "SENIOR", "Bachelor’s degree or equivalent experience
Experience working on federal contracts.
Experience with issue tracking software (i.e., Jira).
Experience with the Software Development Life Cycle process.", 
"Dallas, Texas", "APPLIED", "2022-12-12", "2022-12-12", 7, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _job_id FROM job WHERE _job_id = 7);
/**/
INSERT INTO post (_post_id, _post_notes, _user_id_fk_post, _post_creation_date, _post_update_date)
SELECT 8, "Someone willing to work on a production support teamPossibly willing to work off hours (not necessarily 9-5 AM EST) (need coverage at other times)", 2, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _post_id FROM post WHERE _post_id = 8);
INSERT INTO company (_company_id, _company_name, _company_information, _company_website, _post_id_fk_company, _company_creation_date, _company_update_date)
SELECT 8, "ehLogix", "N/A", "www.google.com", 8, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _company_id FROM company WHERE _company_id = 8);
INSERT INTO job (_job_id, _job_title, _job_type, _job_information, _job_location, _job_status, _job_application_submitted_date, _job_application_dismissed_date, _post_id_fk_job, _job_creation_date, _job_update_date)
SELECT 8, "Production Reconciliation Engineer", "SENIOR", "Task-oriented, self-driven
Scripting of some sort, but probably UNIX Bash scriptingGenerally comfortable with the command line
Excel
Strong Troubleshooting/problem-solving skills
Accounting experience
Database skills Neo4j, MySQL, Redshift
Data analytics experience would probably be helpful
Experience in Java, Spring Boot, Apache Camel, Alfresco Activiti, BPMN may not be totally required but would be helpful I feel.
Great with numbers", 
"Washington DC", "APPLIED", "2020-12-12", "2020-12-12", 8, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _job_id FROM job WHERE _job_id = 8);
/**/
INSERT INTO post (_post_id, _post_notes, _user_id_fk_post, _post_creation_date, _post_update_date)
SELECT 9, "Know Security Principles.", 2, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _post_id FROM post WHERE _post_id = 9);
INSERT INTO company (_company_id, _company_name, _company_information, _company_website, _post_id_fk_company, _company_creation_date, _company_update_date)
SELECT 9, "Security Inc.", "Security Inc. is a leader in digital video surveillance, providing reliable, innovative, and cost-effective video solutions to security professionals, installers, system integrators, consulting firms & resellers of all sizes. With 20 Sales and Distribution Centers in the US and growing, we offer the latest technology of network video surveillance in our comprehensive line of Analog, Hybrid, HD-TVI, HD-SDI, and IP solutions. ", 
"www.google.com", 9, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _company_id FROM company WHERE _company_id = 9);
INSERT INTO job (_job_id, _job_title, _job_type, _job_information, _job_location, _job_status, _job_application_submitted_date, _job_application_dismissed_date, _post_id_fk_job, _job_creation_date, _job_update_date)
SELECT 9, "Entry Level Technical Support", "ENTRY", "Maintain an in-depth knowledge of our complete line of products and services
Embrace and learn the Video Surveillance / CCTV technologies and their applications
Provide product demo and technical training to the sales team and customers
Test and evaluate surveillance cameras, DVR cards, stand-alone or PC-based DVR systems, and peripherals
Build and test PC-based DVR systems to customer's specifications
Provide prompt and effective customer service and technical support
Service customers in an effective and efficient manner; Partner with internal teams in a proactively
Other duties as assigned", 
"Los Angeles, CA", "APPLIED", "2020-12-12", "2020-12-12", 9, STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d'), STR_TO_DATE(CURRENT_DATE(), '%Y-%m-%d')
WHERE NOT EXISTS
    (SELECT _job_id FROM job WHERE _job_id = 9);
/**/
