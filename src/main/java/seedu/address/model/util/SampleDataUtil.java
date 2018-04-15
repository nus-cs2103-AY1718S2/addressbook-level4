package seedu.address.model.util;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.JobbiBot;
import seedu.address.model.ReadOnlyJobbiBot;
import seedu.address.model.internship.Address;
import seedu.address.model.internship.Email;
import seedu.address.model.internship.Industry;
import seedu.address.model.internship.Internship;
import seedu.address.model.internship.Name;
import seedu.address.model.internship.Region;
import seedu.address.model.internship.Role;
import seedu.address.model.internship.Salary;
import seedu.address.model.internship.exceptions.DuplicateInternshipException;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code InternshipBook} with sample data.
 */
public class SampleDataUtil {
    public static Internship[] getSampleInternships() {
        return new Internship[] {
            new Internship(new Name("Yunomori Onsen"), new Salary("800"),
                new Email("phoebe@yunomorionsen.com.sg"), new Address("1 Stadium Place, #02-17/18 Kallang "
                    + "Wave Mall, 397628"), new Industry("Hospitality"), new Region("Central Region"),
                new Role("Design Advertising Interns"), getTagSet()),
            new Internship(new Name("Affinixy Pte Ltd"), new Salary("400"), new Email("charlotte@affinixy.com"),
                new Address("61 Ubi Road 1, Oxley Bizhub 1 #03-40"), new Industry("Media"),
                new Region("Bedok"), new Role("2D Artist Animator Cute Stuff"), getTagSet()),
            new Internship(new Name("Simple Clouds Films"), new Salary("800"), new Email("jamie@simpleclouds.com"),
                new Address("53 Dafne St"), new Industry("Media"),
                new Region("Kembangan"), new Role("Videographer Editor"), getTagSet()),
            new Internship(new Name("Shevron Urgent"), new Salary("800"), new Email("irfan@su.com"),
                new Address("Blk 5 Ang Mo Kio Industrial Park 2A AMK Tech II #06-18"), new Industry("Manufacturing"),
                new Region("Ang Mo Kio"), new Role("Graphic Designer Intern"), getTagSet()),
            new Internship(new Name("Buds Theatre Company"), new Salary("400"), new Email("berniceyu@budstheatre.com"),
                new Address("The Playtent #02-01 180 Joo Chiat Road"), new Industry("Arts Design"),
                new Region("Siglap"), new Role("Arts Administrator Intern"), getTagSet()),
            new Internship(new Name("Mount Studio Pte Ltd"), new Salary("750"), new Email("royb@example.com"),
                new Address("45 Jalan Pemimpin #07-02"), new Industry("Arts Design"), new Region("Marymount"),
                new Role("Photo Studio Assistant Junior Photographer"), getTagSet("saved")),
            new Internship(new Name("Scooterson Inc"), new Salary("850"), new Email("contact@scootersoninc.com"),
                new Address("Blk 3 Chijmes Street 85, #17-31"), new Industry("Automotive"), new Region("Central"),
                new Role("Mechanical Engineer RD Automotive in Singapore at Scooterson"), getTagSet()),
            new Internship(new Name("AV Intelligence"), new Salary("800"), new Email("royb@avintelligence.com"),
                new Address("9 Raffles Boulevard"), new Industry("Finance"), new Region("Raffles"),
                new Role("Accountant"), getTagSet()),
            new Internship(new Name("Momentum Works"), new Salary("500"), new Email("momentumhr@gmail.com"),
                new Address("Raffles Street 85, #21-31"), new Industry("Finance"), new Region("Central Business "
                    + "District"), new Role("Freelance Accountant Finance Intern"), getTagSet()),
            new Internship(new Name("Kao Singapore Private Limited"), new Salary("900"), new Email("ronaldjohn@outlook"
                    + ".com"), new Address("83 Clemenceau Ave, Singapore 239920"), new Industry("Finance"),
                    new Region("Central Business District"), new Role("Audit Associate Internship"), getTagSet()),
            new Internship(new Name("Daimler South East Asia Pte Ltd"), new Salary("1000"),
                    new Email("yu_yan.ho@daimler.com"), new Address("1 Gateway Drive, #15-01 Westgate Tower, 608531"),
                    new Industry("Technology"), new Region("Jurong"), new Role("Corporate Security Intern"),
                    getTagSet("saved")),
            new Internship(new Name("Pivot FinTech"), new Salary("400"), new Email("jennie@pft.com"),
                new Address("143 Cecil Street #08-00 GB Building Singapore 069452"), new Industry("Finance"),
                    new Region("Raffles Place"), new Role("UI Designer"), getTagSet("saved")),
            new Internship(new Name("Allianz SE Insurance Management Asia Pacific"), new Salary("900"),
                    new Email("jennie@example.com"), new Address("Blk 45 Tampines Street 85, #11-31"),
                    new Industry("Finance"), new Region("Marina"), new Role("Intern Risk Management"), getTagSet()),
            new Internship(new Name("Silver Straits Capital Pte Ltd"), new Salary("600"),
                    new Email("admin@silverstraits.co"), new Address("167 Cecil Street #09-107 Singapore 509452"),
                    new Industry("Finance"), new Region("Raffles Place"), new Role("Private Equity Intern"),
                    getTagSet()),
            new Internship(new Name("Hawksburn Capital"), new Salary("800"), new Email("jennie@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Finance"), new Region("Outram"),
                new Role("Research Analyst Intern"), getTagSet()),
            new Internship(new Name("TalentGuru"), new Salary("1000"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Siglap"),
                new Role("Business Consultant Intern"), getTagSet()),
            new Internship(new Name("Wealth Ridge Solutions"), new Salary("2500"), new Email("jennie@example.com"),
                new Address("600 North Bridge Road Parkview Square Tower 2"), new Industry("Consultancy"),
                new Region("Bugis"), new Role("Trainee Sales Consultant"), getTagSet()),
            new Internship(new Name("Prime Solutions Provider"), new Salary("950"), new Email("email@prsolutions.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Bugis"),
                new Role("Management Trainee Consultant"), getTagSet()),
            new Internship(new Name("Prime Solutions Provider Pte Ltd"), new Salary("850"), new Email("jenn@exa.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Bugis"),
                new Role("Sales Marketing Consultant"), getTagSet()),
            new Internship(new Name("JPSO Consultancy"), new Salary("2800"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Bugis"),
                new Role("Customer Relationship Consultant"), getTagSet()),
            new Internship(new Name("Ispring Group"), new Salary("2500"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Bugis"),
                new Role("Sales Consultant"), getTagSet()),
            new Internship(new Name("B Consultancy"), new Salary("2350"), new Email("jennie@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"),
                    new Region("Tanjong Pagar"), new Role("Financial Consultant Internship"), getTagSet()),
            new Internship(new Name("PurpleClick Media Pte Ltd"), new Salary("400"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Tampines"),
                new Role("Digital Consultant"), getTagSet()),
            new Internship(new Name("Advisors Clique"), new Salary("1500"), new Email("jennie@example.com"),
                new Address("Blk 45 Tampines Street 85, #9-31"), new Industry("Consultancy"), new Region("Telok Ayer"),
                new Role("Wealth Management Consultant"), getTagSet()),
            new Internship(new Name("Kind of Blue Pte Ltd"), new Salary("1500"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Consultancy"), new Region("Orchard"),
                new Role("Beauty Sales Consultant"), getTagSet()),
            new Internship(new Name("Rimus Idea"), new Salary("800"), new Email("roybcd@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Engineering"), new Region("Ubi"),
                new Role("Design Consultant"), getTagSet()),
            new Internship(new Name("Horizon Education Group of Companies"), new Salary("800"),
                new Email("roybcd@example.com"), new Address("Blk 45 Tampines Street 85, #11-31"),
                new Industry("Education"), new Region("Kallang"), new Role("Accounts Intern"), getTagSet()),
            new Internship(new Name("Macs Music School Pte Ltd"), new Salary("800"), new Email("jennie@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Education"), new Region("Orchard"),
                new Role("Intern Violin Teacher"), getTagSet()),
            new Internship(new Name("Pan Asia Logistics Investments Pte Ltd"), new Salary("800"),
                new Email("roybcd@example.com"), new Address("21 Changi North Way, Singapore 498774"),
                new Industry("Transportation"), new Region("Changi"), new Role("Finance Intern"), getTagSet()),
            new Internship(new Name("PACE OD Consulting Pte Ltd"), new Salary("400"), new Email("jennie@example.com"),
                new Address("Blk 45 Tampines Street 85, #11-31"), new Industry("Arts"), new Region("Oueenstown"),
                new Role("Content Curation Intern"), getTagSet()),
            new Internship(new Name("Wealth Springs Holdings Pte Ltd Healthcare Technology"), new Salary("600"),
                new Email("wsholdings@example.com"), new Address("985 Bukit Timah Road"), new Industry("Healthcare"),
                new Region("Bukit Timah"), new Role("Intern New Business Development"), getTagSet()),
            new Internship(new Name("ClearSK Healthcare Pte Ltd"), new Salary("1200"),
                new Email("csk@example.com"), new Address("125 Novena Street 13"), new Industry("Healthcare"),
                new Region("Novena"), new Role("Financial Data Business Analyst"), getTagSet("saved")),
            new Internship(new Name("ClearSK Healthcare Pte Ltd"), new Salary("1000"),
                new Email("csk@example.com"), new Address("10 Sinaran Drive"), new Industry("Healthcare"),
                new Region("Novena"), new Role("Accounting Finance Analyst"), getTagSet()),
            new Internship(new Name("WE Communications"), new Salary("800"),
                new Email("wecomms@example.com"), new Address("77 Robinson Road"), new Industry("Media"),
                new Region("Orchard"), new Role("Account Servicing Intern Healthcare"), getTagSet()),
            new Internship(new Name("ClearSK Healthcare Pte Ltd"), new Salary("1000"),
                new Email("csk@example.com"), new Address("126 Novena Street 13"), new Industry("Healthcare"),
                new Region("Novena"), new Role("Operations Marketing Assistant"), getTagSet()),
            new Internship(new Name("Recruit Express Pte Ltd"), new Salary("1150"),
                new Email("rexpress@example.com"), new Address("126 Novena Street 13"), new Industry("Healthcare"),
                new Region("Orchard"), new Role("Contract Perm Recruiter Healthcare Sector"), getTagSet()),
            new Internship(new Name("KosmodeHealth"), new Salary("2500"),
                new Email("kosmode@example.com"), new Address("15 Libra Drive"), new Industry("Healthcare"),
                new Region("Bishan"), new Role("Project Executive Manager Biochemistry Startup"), getTagSet()),
            new Internship(new Name("Royal Vending Pte Ltd"), new Salary("1000"),
                new Email("royalvv@example.com"), new Address("Bukit Batok St 24 Blk 2 #04-04"),
                new Industry("Hospitality"), new Region("Bukit Batok"),
                new Role("Sentosa Hospitality Internship Opportunity"), getTagSet()),
            new Internship(new Name("Far East Hospitality"), new Salary("800"),
                new Email("FEhosp@example.com"), new Address("Centre Square St 13"), new Industry("Hospitality"),
                new Region("Central"), new Role("Admin Executive Marketing"), getTagSet()),
            new Internship(new Name("Yerra Solutions"), new Salary("400"),
                new Email("Yerrasol@example.com"), new Address("8 eu Tong Sen St #14-93"), new Industry("Legal"),
                new Region("Central"), new Role("Legal HR Admin Intern"), getTagSet()),
            new Internship(new Name("Mc Corporate Services Pte Ltd"), new Salary("450"),
                new Email("Mccorp@example.com"), new Address("1 Coleman Street"), new Industry("Legal"),
                new Region("Central"), new Role("Legal Corporate Secretarial Internship"), getTagSet()),
            new Internship(new Name("Schneider Electric Pte Ltd"), new Salary("800"),
                new Email("Schneider@example.com"), new Address("50 Kallang Ave"), new Industry("Legal"),
                new Region("Central"), new Role("Legal Intern"), getTagSet()),
            new Internship(new Name("PropertyLimBrothersMedia"), new Salary("1500"),
                new Email("PLBM11@example.com"), new Address("Central Street 13"), new Industry("Media"),
                new Region("Central"), new Role("Full Time Videographer"), getTagSet()),
            new Internship(new Name("Allies Of Skin"), new Salary("800"),
                new Email("AlliesOShosp@example.com"), new Address("3 Jln Kledek"), new Industry("Media"),
                new Region("Central"), new Role("Editorial and Social Media Intern"), getTagSet()),
            new Internship(new Name("Piece Future Pte Ltd"), new Salary("500"),
                new Email("PFpteltd@example.com"), new Address("45 Middle Road #11-09"), new Industry("Business"),
                new Region("Central"), new Role("Business Analyst"), getTagSet("saved")),
            new Internship(new Name("Effectual Knowledge Services Pte Ltd"), new Salary("800"),
                new Email("EKSpteltd@example.com"), new Address("5 Shenton Way #10-01"), new Industry("Business"),
                new Region("Central"), new Role("Business Development Associate"), getTagSet()),
            new Internship(new Name("Tradex Systems Pte Ltd"), new Salary("800"),
                new Email("Tradex@example.com"), new Address("20 Collyer Quay #23-01"), new Industry("Technology"),
                new Region("Central"), new Role("Business Development Associate"), getTagSet()),
            new Internship(new Name("Riverview Mobile Solutions"), new Salary("600"),
                new Email("RVMS@example.com"), new Address("8 Burn road #12-02"), new Industry("Technology"),
                new Region("West"), new Role("Technology Analyst Intern"), getTagSet("saved")),
            new Internship(new Name("Caproasia"), new Salary("800"),
                new Email("Caproasia@example.com"), new Address("10 Shenton way #11-02"), new Industry("Technology"),
                new Region("CBD"), new Role("Web Technology Intern"), getTagSet("saved")),
            new Internship(new Name("Cochat"), new Salary("400"),
                new Email("Cochat@example.com"), new Address("10 Serangoon Road #08-10"), new Industry("Healthcare"),
                new Region("Serangoon"), new Role("Social Enterprise Marcom Writer"), getTagSet()),
            new Internship(new Name("Bettr Barista Coffee Academy"), new Salary("800"),
                new Email("BettrBari@example.com"), new Address("37 Mactaggart Road #07-03"), new Industry("Services"),
                new Region("West"), new Role("Social Enterprise Intern"), getTagSet()),
            new Internship(new Name("Aii International Pte Ltd"), new Salary("400"),
                new Email("AiiINT@example.com"), new Address("442 Orchard Road #03-01"), new Industry("Marketing"),
                new Region("Orchard"), new Role("Sales and Marketing Social Enterprise"), getTagSet()),

        };
    }

    public static ReadOnlyJobbiBot getSampleJobbiBot() {
        try {
            JobbiBot sampleAb = new JobbiBot();
            for (Internship sampleInternship : getSampleInternships()) {
                sampleAb.addInternship(sampleInternship);
            }
            return sampleAb;
        } catch (DuplicateInternshipException e) {
            throw new AssertionError("sample data cannot contain duplicate internships", e);
        }
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        HashSet<Tag> tags = new HashSet<>();
        for (String s : strings) {
            tags.add(new Tag(s));
        }

        return tags;
    }

}
