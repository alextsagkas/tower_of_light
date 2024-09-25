package items.equipables.weapons;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

public final class Damage {
    public enum DamageType {SWING, THRUST, MAGICAL}

    public static final class Dice {
        private final Integer numberOfDices;
        private final Integer diceSides;
        private Integer constantAddend;
        private final Random rand;

        public Dice(Integer numberOfDices, Integer diceSides, Integer constantAddend) {
            this.numberOfDices = numberOfDices;
            this.diceSides = diceSides;
            this.constantAddend = constantAddend;
            this.rand = new Random();
        }

        /**
         * @param string It describes the number of dices, their sides and the constant factor
         *               to add. The form of the string is:
         *               {@code
         *               <numberOfDices>d<diceSides> + <constantAddend>
         *               }.
         */
        public Dice(String string) {
            String[] parts = string.split("[d+]");
            this.numberOfDices = Integer.valueOf(parts[0].trim());
            this.diceSides = Integer.valueOf(parts[1].trim());
            this.constantAddend = Integer.valueOf(parts[2].trim());
            this.rand = new Random();
        }

        public Dice(Dice other) {
            this.numberOfDices = other.numberOfDices;
            this.diceSides = other.diceSides;
            this.constantAddend = other.constantAddend;
            this.rand = new Random();
        }

        public void addConstantAddend(Integer constantAddend) {
            this.constantAddend += constantAddend;
        }

        public @NotNull Integer roll() {
            if (diceSides > 0) {
                return numberOfDices * rand.nextInt(diceSides) + constantAddend;
            } else {
                return constantAddend;
            }
        }

        @Override
        public String toString() {
            return "Dice{" + numberOfDices + "d" + diceSides + " + " + constantAddend + "}";
        }

        public static void main(String[] args) {
            Dice dice1 = new Dice(2, 6, 1);
            System.out.printf(
                    "%dd%d + %d\n",
                    dice1.numberOfDices,
                    dice1.diceSides,
                    dice1.constantAddend
            );

            Dice dice2 = new Dice("2d6+1");
            System.out.printf(
                    "%dd%d + %d\n",
                    dice2.numberOfDices,
                    dice2.diceSides,
                    dice2.constantAddend
            );
        }
    }

    final DamageType damageType;
    final Dice damageDice;

    public Damage(
            DamageType damageType,
            Dice damageDice
    ) {
        this.damageType = damageType;
        this.damageDice = damageDice;
    }

    public Damage(Damage other) {
        this.damageType = other.damageType;
        this.damageDice = new Dice(other.damageDice);
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public Integer getDamageAmount() {
        return damageDice.roll();
    }

    public void enhance(Integer damage) {
        this.damageDice.addConstantAddend(damage);
    }

    @Override
    public String toString() {
        return "Damage{" +
               "damageType=" + damageType +
               ", damageDice=" + damageDice +
               "}";
    }
}
