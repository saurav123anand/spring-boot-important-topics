1. Owing Side

Owing side entity is entity that contains foreign key
if One entity can't exist without other entity then that entity becomes owing side
for example
Patient can exist without Appointment but Appointment can't exist without Patient so Appointment will be the owing side 
that thing we can apply for one-to-one mapping also where insurance should ideally become the owing side, 
but we have made Patient as an owing side because it's just one-to-one so we can make any side owing\

2. Even in bidirectional mapping:
Write operations must be done on the owning side
Inverse side is only for reading/navigation
Saving from inverse side does nothing to the relationship colum

Even though in one-to-may mappings , many-to-one side is owning side generally bit we don't write Cascade
type on the owning side like one-to-one mapping since although it's owning side but its child only 
for exmaple Appoitment is owining side nad Paremt is inverse side but we don't write Cascade type on Appointment
side since Appointment is child of Patient , for example we don't want to delete Patient if we delete Appointment
so iff we need to use Cascade type then we need to use it on inverse side(Patient side) but carefully 



ChatGPT said:

In JPA/Hibernate, orphanRemoval = true is a setting you add to a relationship annotation like:

@OneToOne(orphanRemoval = true)
@OneToMany(mappedBy = "...", orphanRemoval = true)


It controls what happens to child entities when they are removed from the parent’s relationship.

✅ Simple Meaning

If a child entity is no longer referenced by the parent, Hibernate will automatically DELETE that child from the database.

That child becomes an orphan, and JPA removes it.

📌 Without orphanRemoval = true

Removing a child from the list or setting it to null does NOT delete it from DB.

The foreign key becomes null or the record stays as it is.

📌 With orphanRemoval = true

When you do:

Example 1 — OneToOne
patient.setInsurance(null);


Hibernate will DELETE the insurance row automatically.

Example 2 — OneToMany
patient.getAppointments().remove(appointment);


Hibernate will DELETE that appointment from DB, not just remove it from the list.

🧠 Why is it useful?

It ensures database consistency, especially when:

A child entity must not exist without a parent

Example: Address of a User, Insurance of a Patient, Images of a Product, etc.

🚨 Difference between orphanRemoval = true and cascade = CascadeType.REMOVE
Feature	orphanRemoval	CascadeType.REMOVE
Delete child when removed from parent	✅ Yes	❌ No
Delete child when parent is deleted	❌ No	✅ Yes
Based on relationship reference	YES	NO